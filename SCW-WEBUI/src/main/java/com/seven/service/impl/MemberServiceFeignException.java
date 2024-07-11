package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 16:42
 */
import com.seven.response.AppResponse;
import com.seven.service.MemberServiceFeign;
import com.seven.vo.UserAddressVo;
import com.seven.vo.UserRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MemberServiceFeignException implements MemberServiceFeign {
    @Override
    public AppResponse<UserRespVo> login(String loginacct, String password) {
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【登录】");
        return fail;
    }

    @Override
    public AppResponse<UserRespVo> findUser(Integer id) {
        AppResponse<UserRespVo> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【获取用户信息】");
        return fail;
    }

    @Override
    public AppResponse<List<UserAddressVo>> address(String accessToken) {
        AppResponse<List<UserAddressVo>> fail = AppResponse.fail(null);
        fail.setMsg("调用远程服务器失败【获取用户地址列表】");
        return fail;
    }
}