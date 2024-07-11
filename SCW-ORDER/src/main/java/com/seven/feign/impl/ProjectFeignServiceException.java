package com.seven.feign.impl;

/**
 * @author: seven
 * @since: 2024/7/11 16:07
 */
import com.seven.entity.TReturn;
import com.seven.feign.ProjectFeignService;
import com.seven.response.AppResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectFeignServiceException implements ProjectFeignService {
    @Override
    public AppResponse<List<TReturn>> detailsReturn(Integer projectId) {
        AppResponse<List<TReturn>> fail = AppResponse.fail(null);
        fail.setMsg("远程服务调用失败");
        return fail;
    }
}

