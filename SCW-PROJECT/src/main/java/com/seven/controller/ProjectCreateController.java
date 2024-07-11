package com.seven.controller;

import com.alibaba.fastjson.JSON;
import com.seven.entity.TReturn;
import com.seven.enums.ProjectStatusEnume;
import com.seven.response.AppResponse;
import com.seven.service.ProjectCreateService;
import com.seven.vo.BaseVo;
import com.seven.vo.ProjectBaseInfoVo;
import com.seven.vo.ProjectRedisStorageVo;
import com.seven.vo.ProjectReturnVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: seven
 * @since: 2024/7/11 13:32
 */
@Api(tags = "项目基本功能模块（创建、保存、项目信息获取、文件上传等）")
@Slf4j
@RequestMapping("/project")
@RestController
public class ProjectCreateController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectCreateService projectCreateService;

    @ApiOperation("项目发起第1步-阅读同意协议")
    @GetMapping("/init")
    public AppResponse<String> init(BaseVo vo) {

        String token = vo.getAccessToken();
        //通过令牌获取项目编号
        String memberId = redisTemplate.opsForValue().get(token);
        if (StringUtils.isEmpty(memberId)) {
            return AppResponse.fail("无权限，请先登录");
        }

        //转换memberid为整数
        Integer id = Integer.valueOf(memberId);
        //根据id创建临时信息保存到redis
        String projectToken = projectCreateService.initCreateProject(id);

        //响应
        return AppResponse.ok(projectToken);

    }

    @Value("${temp.profix}")
    private String profix;

    @ApiOperation("项目发起第2步-保存项目的基本信息")
    @GetMapping("/savebaseInfo")
    public AppResponse<String> savebaseInfo(ProjectBaseInfoVo vo) {
        String json = redisTemplate.opsForValue().get(profix + vo.getProjectToken());
        ProjectRedisStorageVo redisStorageVo = JSON.parseObject(json, ProjectRedisStorageVo.class);
        BeanUtils.copyProperties(vo, redisStorageVo);
        redisTemplate.opsForValue().set(profix + vo.getProjectToken(), JSON.toJSONString(redisStorageVo));
        return AppResponse.ok("OK");
    }


    @ApiOperation("项目发起第3步-项目保存项目回报信息")
    @PostMapping("/savereturn")
    public AppResponse<String> saveReturnInfo(@RequestBody List<ProjectReturnVo> pro) {
        //从第一个回报信息中获取projectToken
        String projectToken = pro.get(0).getProjectToken();
        //根据projectToken，从redis中获取完整信息
        String json = redisTemplate.opsForValue().get(profix + projectToken);
        //TODO 注意：这里使用的是ProjectRedisStorageVo，他包含ProjectBaseInfoVo的属性
        ProjectRedisStorageVo baseInfoVo = JSON.parseObject(json, ProjectRedisStorageVo.class);
        //循环，将回报视图ProjectReturnVo转换成回报实体类TReturn
        List<TReturn> list = new ArrayList<>();
        for (ProjectReturnVo vo : pro) {
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(vo, tReturn);
            list.add(tReturn);
        }
        //添加到项目信息中，写回redis
        baseInfoVo.setProjectReturns(list);
        redisTemplate.opsForValue().set(profix + projectToken, JSON.toJSONString(baseInfoVo));
        return AppResponse.ok("OK");
    }


    @ApiOperation("项目发起第4步-项目保存项目回报信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken", value = "用户token", required = true),
            @ApiImplicitParam(name = "projectToken", value = "项目token", required = true),
            @ApiImplicitParam(name = "ops", value = "操作类型，0草稿，1提交", required = true)
    })
    @PostMapping("/submit")
    public AppResponse<Object> submit(String accessToken, String projectToken, Integer ops) {
        //1校验当前用户是否有效
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (memberId == null) return AppResponse.fail("会话不存在");

        //读取项目信息
        String json = redisTemplate.opsForValue().get(profix + projectToken);
        ProjectRedisStorageVo vo = JSON.parseObject(json, ProjectRedisStorageVo.class);
        // vo.setMemberid(Integer.parseInt(memberId));

        if (ops == null) return AppResponse.fail("状态为空");

        //更新项目状态 ,保存项目
        if (ops.equals(ProjectStatusEnume.DRAFT.getCode())) {
            projectCreateService.saveProjectInfo(ProjectStatusEnume.DRAFT, vo);
        } else {
            projectCreateService.saveProjectInfo(ProjectStatusEnume.SUBMIT_AUTH, vo);
        }
        return AppResponse.ok("保存成功");
    }

}

