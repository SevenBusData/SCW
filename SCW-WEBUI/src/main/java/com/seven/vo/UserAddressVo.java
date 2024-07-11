package com.seven.vo;

/**
 * @author: seven
 * @since: 2024/7/11 16:57
 */
import lombok.Data;

import java.io.Serializable;

@Data
public class UserAddressVo implements Serializable {

    //地址id
    private Integer id ;

    //会员地址
    private String address ;
}

