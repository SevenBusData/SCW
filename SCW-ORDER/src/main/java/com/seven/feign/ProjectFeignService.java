package com.seven.feign;

/**
 * @author: seven
 * @since: 2024/7/11 16:06
 */
import com.seven.entity.TReturn;
import com.seven.feign.impl.ProjectFeignServiceException;
import com.seven.response.AppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "SCW-PROJECT", fallback = ProjectFeignServiceException.class)
public interface ProjectFeignService {
    //http://localhost:8001/project/details/returns/8
    @GetMapping("/project/details/returns/{projectId}")
    public AppResponse<List<TReturn>> detailsReturn(@PathVariable("projectId") Integer projectId);
}

