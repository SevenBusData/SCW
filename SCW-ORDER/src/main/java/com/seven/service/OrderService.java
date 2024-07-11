package com.seven.service;

import com.seven.entity.TOrder;
import com.seven.vo.OrderInfoSubmitVo;

/**
 * @author: seven
 * @since: 2024/7/11 16:09
 */
public interface OrderService {
    public TOrder save(OrderInfoSubmitVo vo);
}

