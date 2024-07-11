package com.seven.service;

/**
 * @author: seven
 * @since: 2024/7/11 16:58
 */
import com.seven.config.FeignConfig;
import com.seven.response.AppResponse;
import com.seven.service.impl.OrderServiceFeignException;
import com.seven.vo.OrderFormInfoSubmitVo;
import com.seven.vo.TOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "SCW-ORDER",fallback = OrderServiceFeignException.class,configuration= FeignConfig.class)
public interface OrderServiceFeign {

    @PostMapping("/order/createOrder")
    AppResponse<TOrder> createOrder(@RequestBody OrderFormInfoSubmitVo vo);
}

