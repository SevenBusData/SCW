package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 16:59
 */
import com.seven.response.AppResponse;
import com.seven.service.OrderServiceFeign;
import com.seven.vo.OrderFormInfoSubmitVo;
import com.seven.vo.TOrder;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceFeignException implements OrderServiceFeign {
    @Override
    public AppResponse<TOrder> createOrder(OrderFormInfoSubmitVo vo) {
        AppResponse<TOrder> response = AppResponse.fail(null);
        response.setMsg("远程调用失败【订单】");
        return response;
    }
}