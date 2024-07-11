package com.seven.service;

/**
 * @author: seven
 * @since: 2024/7/11 16:41
 */
import com.seven.config.FeignConfig;
import com.seven.response.AppResponse;
import com.seven.service.impl.MemberServiceFeignException;
import com.seven.vo.UserAddressVo;
import com.seven.vo.UserRespVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "SCW-USER",configuration = FeignConfig.class,fallback = MemberServiceFeignException.class)
public interface MemberServiceFeign {
    @PostMapping("/login/login")
    public AppResponse<UserRespVo> login(@RequestParam("username") String loginacct, @RequestParam("password") String password);

    /**
     * 根据用户编号，获取用户基本信息
     * @param id
     * @return
     */
    @GetMapping("/user/findUser/{id}")
    public AppResponse<UserRespVo> findUser(@PathVariable("id") Integer id);

    /**
     * 获取当前登录账号的地址
     * @param accessToken
     * @return
     */
    @GetMapping("/login/info/address")
    public AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken") String accessToken);

}