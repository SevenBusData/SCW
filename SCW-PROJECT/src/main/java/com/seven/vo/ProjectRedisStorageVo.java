package com.seven.vo;

/**
 * @author: seven
 * @since: 2024/7/11 13:27
 */
import com.seven.entity.TReturn;
import lombok.Data;

import java.util.List;

@Data
public class ProjectRedisStorageVo extends ProjectBaseInfoVo {
    private Integer memberid;//会员id
    private List<TReturn> projectReturns;//项目回报
}