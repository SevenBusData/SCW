package com.seven.entity;

/**
 * @author: seven
 * @since: 2024/7/11 11:00
 */
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@ApiModel
@Table(name = "t_member_address")
public class TMemberAddress implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("地址id")
    private Integer id;

    private Integer memberid;
    @ApiModelProperty("会员地址")
    private String address;
}