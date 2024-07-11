package com.seven.controller;

/**
 * @author: seven
 * @since: 2024/7/11 16:43
 */
import com.alibaba.fastjson.JSON;
import com.seven.response.AppResponse;
import com.seven.service.MemberServiceFeign;
import com.seven.service.ProjectServiceFeign;
import com.seven.vo.ProjectVo;
import com.seven.vo.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller
@Slf4j
public class DispatcherController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;


    @RequestMapping("/doLogin")
    public String doLogin(String loginacct, String password, HttpSession session) {
        AppResponse<UserRespVo> resp = memberServiceFeign.login(loginacct, password);
        UserRespVo userRespVo = resp.getData();
        log.info("用户名：{},密码：{}", loginacct, password);
        log.info("登陆用户数据:{}", userRespVo);
        if (userRespVo == null) return "redirect:login";
        session.setAttribute("sessionMember", userRespVo);
        String preUrl = (String) session.getAttribute("preUrl");
        if (StringUtils.isEmpty(preUrl))
            return "redirect:/";
        else
            return "redirect:/" + preUrl;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @GetMapping("/")
    public String index(Model model) {
        String projectStr = redisTemplate.opsForValue().get("projectStr");
        if (projectStr == null) {
            AppResponse<List<ProjectVo>> appResponse = projectServiceFeign.all();
            List<ProjectVo> voList = appResponse.getData();
            redisTemplate.opsForValue().set("projectStr", JSON.toJSONString(voList), 1, TimeUnit.HOURS);
            model.addAttribute("projectList", voList);
        } else {
            model.addAttribute("projectList", JSON.parseArray(projectStr, ProjectVo.class));
        }

        return "index";
    }
}

