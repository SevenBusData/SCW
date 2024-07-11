package com.seven.controller;

/**
 * @author: seven
 * @since: 2024/7/11 16:55
 */
import com.seven.entity.TMember;
import com.seven.response.AppResponse;
import com.seven.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "获取会员信息/更新个人信息/获取用户收货地址")
@RestController
@RequestMapping("/user")
public class UserInfoController {


    @Autowired
    private MemberService userService;

    //根据用户编号获取用户信息
    @GetMapping("/findUser/{id}")
    public AppResponse<TMember> findUser(@PathVariable("id") Integer id) {
        TMember tmember = userService.findTmemberById(id);
        return AppResponse.ok(tmember);
    }

}

