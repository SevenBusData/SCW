package com.seven.controller;

/**
 * @author: seven
 * @since: 2024/7/11 16:52
 */
import com.seven.response.AppResponse;
import com.seven.service.MemberServiceFeign;
import com.seven.service.ProjectServiceFeign;
import com.seven.vo.ProjectDetailVo;
import com.seven.vo.ReturnPayConfirmVo;
import com.seven.vo.UserAddressVo;
import com.seven.vo.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {


    @Autowired
    private ProjectServiceFeign projectServiceFeign;

    @RequestMapping("/projectInfo")
    public String projectInfo(Integer id, Model model, HttpSession session) {
        AppResponse<ProjectDetailVo> vo = projectServiceFeign.detailsInfo(id);
        ProjectDetailVo data = vo.getData();
        model.addAttribute("DetailVo", data);
        session.setAttribute("DetailVo", data);
        return "project/project";
    }

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @RequestMapping("/confirm/returns/{projectId}/{returnId}")
    public String returnInfo(@PathVariable("projectId") Integer projectId, @PathVariable("returnId") Integer returnId, Model model, HttpSession session) {
        //从session获取项目详细信息
        ProjectDetailVo projectDetailVo = (ProjectDetailVo) session.getAttribute("DetailVo");
        //根据项目回报编号，获取项目回报信息
        AppResponse<ReturnPayConfirmVo> appResponse = projectServiceFeign.returnInfo(returnId);
        //获取项目回报信息
        ReturnPayConfirmVo returnPayConfirmVo = appResponse.getData();

        //设置项目回报的项目id
        returnPayConfirmVo.setProjectId(projectId);
        //设置项目回报的项目名称
        returnPayConfirmVo.setProjectName(projectDetailVo.getName());


        //根据项目发起方id，获取项目发起方名称
        AppResponse<UserRespVo> memberServiceAppResponse = memberServiceFeign.findUser(projectDetailVo.getMemberid());
        UserRespVo userRespVo = memberServiceAppResponse.getData();
        //设置发起方名称
        returnPayConfirmVo.setMemberName(userRespVo.getRealname());
        //添加项目回报信息到session
        session.setAttribute("returnConfirm", returnPayConfirmVo);
        //添加项目回报信息到Model
        model.addAttribute("returnConfirm", returnPayConfirmVo);

        return "project/pay-step-1";
    }

    @GetMapping("/confirm/order/{num}")
    public String confirmOrder(@PathVariable("num") Integer num, Model model, HttpSession session) {
        //检查是否登陆，如果没登录，保存当前路径，并跳转到登录页
        UserRespVo userRespVo = (UserRespVo) session.getAttribute("sessionMember");
        if (userRespVo == null) {
            session.setAttribute("preUrl", "project/confirm/order/" + num);
            return "redirect:/login";
        }
        //如果登陆成功，则根据accToken读取地址列表
        String accessToken = userRespVo.getAccessToken();
        List<UserAddressVo> address = memberServiceFeign.address(accessToken).getData();
        model.addAttribute("addresses", address);

        //读取回报对象，并设置数量、金额
        ReturnPayConfirmVo returnVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirm");
        returnVo.setNum(num);
        returnVo.setTotalPrice(new BigDecimal(returnVo.getSupportmoney() * num + returnVo.getFreight()));
        session.setAttribute("returnConfirmSession", returnVo);

        return "project/pay-step-2";
    }
}

