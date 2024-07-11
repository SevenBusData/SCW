package com.seven.controller;

/**
 * @author: seven
 * @since: 2024/7/10 17:51
 */
import com.seven.entity.TMember;
import com.seven.entity.TMemberAddress;
import com.seven.response.AppResponse;
import com.seven.service.MemberService;
import com.seven.utils.SmsTemplate;
import com.seven.vo.UserRegistVo;
import com.seven.vo.UserRespVo;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
@Api(tags = "用户注册，登陆")
public class LoginController {

    private static final Logger log = LogManager.getLogger(LoginController.class);


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SmsTemplate smsTemplate;

    @GetMapping("/sendCode")
    @ApiOperation(value = "发送验证码")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataTypeClass = String.class)
    )
    public AppResponse<String> sendCode(String mobile) {
        //创建ID
        String code = UUID.randomUUID().toString().substring(0, 4);
        //缓存code
        redisTemplate.opsForValue().set(mobile, code);
        //发送
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        querys.put("param", "code:" + code);
        querys.put("tpl_id", "TP1711063");
        boolean success = smsTemplate.send(querys);
        return success ? AppResponse.ok("发送成功") : AppResponse.fail("发送失败");

    }


    @Autowired
    private MemberService memberService;


    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public AppResponse<Object> regist(UserRegistVo registVo) {

        //1、从redis读取短信验证码
        String smscode = (String) redisTemplate.opsForValue().get(registVo.getLoginacct());
        //判断短信验证码是否为空
        if (!StringUtils.isEmpty(smscode)) {

            //如果验证码存在比对用户输入验证码和redis读取到的验证码是否一致
            boolean is = smscode.equals(registVo.getCode());

            if (is) {
                //如果验证码一致
                //创建注册实体Tmemer
                TMember member = new TMember();
                //复制vo 属性值到 实体
                BeanUtils.copyProperties(registVo, member);
                //把用户注册信息保存到数据库
                try {
                    memberService.registerUser(member);
                    log.debug("用户注册成功:{}" ,member.getLoginacct());
                    //注册完成移除redis的短信验证码
                    redisTemplate.delete(member.getLoginacct());

                    //返回注册成功响应结果
                    return AppResponse.ok("用户注册成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("用户注册失败", member.getLoginacct());
                    return AppResponse.fail("用户注册失败");
                }
            } else {
                //验证码比对失败，返回注册失败
                return AppResponse.fail("短信验证码输入错误");

            }
        } else {
            //验证码从redis读取不到，返回验证码过期错误
            return AppResponse.fail("验证码已经过期,请重新获取");
        }

    }

    @PostMapping("/login")
    @ApiOperation("登陆")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "username", value = "用户名", required = true),
                    @ApiImplicitParam(name = "password", value = "密码", required = true)
            }
    )
    public AppResponse<UserRespVo> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        //验证登陆
        TMember tMember = memberService.login(username, password);
        //如果返回null则登陆失败
        if (tMember == null) {
            log.error("用户不存在");
            AppResponse<UserRespVo> fail = AppResponse.fail(null);
            fail.setMsg("用户不存在");
            return fail;
        }
        //如果登陆成功，则拷贝tmember 到视图对象
        UserRespVo vo = new UserRespVo();
        BeanUtils.copyProperties(tMember, vo);
        //使用UUID生成一个token值，根据令牌缓存视图对象
        String token = UUID.randomUUID().toString().replace("-", "");
        vo.setAccessToken(token);
        //redis中记录的是token和memberid的对应关系
        redisTemplate.opsForValue().set(token, tMember.getId() + "", 2, TimeUnit.HOURS);
        return AppResponse.ok(vo);
    }

    @GetMapping("/address")
    @ApiOperation("获取用户地址")
    @ApiImplicitParams(@ApiImplicitParam(name = "accessToken", value = "令牌", required = true))
    public AppResponse<Object> address(String accessToken) {
        Integer memberId = Integer.parseInt(redisTemplate.opsForValue().get(accessToken) + "");
        if (memberId == null) return AppResponse.fail("会话不存在");

        List<TMemberAddress> addressList = memberService.findAddressByMemberId(memberId);
        return AppResponse.ok(addressList);
    }
}


