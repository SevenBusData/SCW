package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 16:48
 */
import com.seven.response.AppResponse;
import com.seven.service.ProjectServiceFeign;
import com.seven.vo.ProjectDetailVo;
import com.seven.vo.ProjectVo;
import com.seven.vo.ReturnPayConfirmVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectServiceFeignException implements ProjectServiceFeign {
    @Override
    public AppResponse<List<ProjectVo>> all() {
        AppResponse<List<ProjectVo>> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【查询首页热点项目】");
        return response;
    }

    @Override
    public AppResponse<ProjectDetailVo> detailsInfo(Integer projectId) {
        AppResponse<ProjectDetailVo> appResponse = AppResponse.fail(null);
        appResponse.setMsg("远程调用失败【查询项目详情】");
        return appResponse;
    }

    @Override
    public AppResponse<ReturnPayConfirmVo> returnInfo(Integer returnId) {
        AppResponse<ReturnPayConfirmVo> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【项目回报】");
        return response;
    }
}