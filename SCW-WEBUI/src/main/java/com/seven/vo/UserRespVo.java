package com.seven.vo;

/**
 * @author: seven
 * @since: 2024/7/11 16:41
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
public class UserRespVo implements Serializable {

    @ApiModelProperty("访问令牌，请妥善保管，以后每次请求都要带上")
    private String accessToken;//访问令牌
    private String loginacct; //存储手机号
    private String username;
    private String email;
    private String authstatus;
    private String usertype;
    private String realname;
    private String cardnum;
    private String accttype;

}