package com.seven.service;

/**
 * @author: seven
 * @since: 2024/7/11 16:48
 */
import com.seven.config.FeignConfig;
import com.seven.response.AppResponse;
import com.seven.service.impl.ProjectServiceFeignException;
import com.seven.vo.ProjectDetailVo;
import com.seven.vo.ProjectVo;
import com.seven.vo.ReturnPayConfirmVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SCW-PROJECT", configuration = FeignConfig.class, fallback = ProjectServiceFeignException.class)
public interface ProjectServiceFeign {
    @GetMapping("/project/all")
    public AppResponse<List<ProjectVo>> all();

    @GetMapping("/project/details/info/{projectId}")
    public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId);

    @GetMapping("/project/returns/info/{returnId}")
    public AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("returnId") Integer returnId);


}