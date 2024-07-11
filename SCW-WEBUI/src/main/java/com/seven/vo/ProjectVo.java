package com.seven.vo;

/**
 * @author: seven
 * @since: 2024/7/11 16:48
 */
import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectVo implements Serializable {

    // 会员id
    private Integer memberid;
    //项目id
    private Integer id;
    // 项目名称
    private String name;
    // 项目简介
    private String remark;
    // 项目头部图片
    private String headerImage;
}