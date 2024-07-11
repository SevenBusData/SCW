package com.seven.service.impl;

/**
 * @author: seven
 * @since: 2024/7/11 16:09
 */
import com.seven.entity.TOrder;
import com.seven.entity.TReturn;
import com.seven.enums.OrderStatusEnumes;
import com.seven.feign.ProjectFeignService;
import com.seven.repository.OrderRepository;
import com.seven.service.OrderService;
import com.seven.util.AppDateUtils;
import com.seven.vo.OrderInfoSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectFeignService projectFeignService;
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public TOrder save(OrderInfoSubmitVo vo) {
        String accessToken = vo.getAccessToken();
        String memberId = redisTemplate.opsForValue().get(accessToken);
        TOrder tOrder = new TOrder();
        BeanUtils.copyProperties(vo, tOrder);
        tOrder.setMemberid(Integer.parseInt(memberId));
        tOrder.setOrdernum(UUID.randomUUID().toString().replace("-", ""));
        tOrder.setCreatedate(AppDateUtils.getFormatTime());
        tOrder.setStatus(OrderStatusEnumes.UNPAY.getCode() + "");

        List<TReturn> returnList = projectFeignService.detailsReturn(vo.getProjectid()).getData();
        for (TReturn tReturn : returnList) {
            if (tReturn.getId().equals(vo.getReturnid())) {
                BeanUtils.copyProperties(tReturn, tOrder);
                //金额=单价*数量+运费
                tOrder.setMoney(tReturn.getSupportmoney() * vo.getRtncount() + tReturn.getFreight());
                orderRepository.save(tOrder);
                return tOrder;
            }
        }
        return null;
    }
}

