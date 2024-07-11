package com.seven.vo;

/**
 * @author: seven
 * @since: 2024/7/11 16:51
 */
import lombok.Data;

import java.io.Serializable;


@Data
public class ProjectReturnVo implements Serializable {

    private String projectToken;

    private Integer id;

    private Integer projectId;

    private Byte type;

    private Integer supportmoney;

    private String content;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private Byte invoice;

    private Integer rtndate;
}