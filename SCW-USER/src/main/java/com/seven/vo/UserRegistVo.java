package com.seven.vo;

/**
 * @author: seven
 * @since: 2024/7/11 10:49
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UserRegistVo {
    @ApiModelProperty("手机号")
    private String loginacct;
    @ApiModelProperty("密码")
    private String userpswd;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("邮箱")
    private String email;
}

