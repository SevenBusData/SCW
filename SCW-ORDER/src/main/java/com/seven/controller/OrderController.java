package com.seven.controller;

/**
 * @author: seven
 * @since: 2024/7/11 16:08
 */
import com.seven.entity.TOrder;
import com.seven.entity.TReturn;
import com.seven.feign.ProjectFeignService;
import com.seven.response.AppResponse;
import com.seven.service.OrderService;
import com.seven.vo.OrderInfoSubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("订单控制器")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ProjectFeignService projectFeignService;

    @ApiOperation("根据项目id，查询回报列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "projectId", value = "项目id", dataTypeClass = Integer.class)
    })
    @GetMapping("/detailReturn")
    public AppResponse<List<TReturn>> detailReturn(Integer projectId) {
        return projectFeignService.detailsReturn(projectId);
    }

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    @ApiOperation("保存订单")
    public AppResponse<TOrder> save(@RequestBody OrderInfoSubmitVo vo) {
        TOrder order = orderService.save(vo);
        AppResponse<TOrder> orderAppResponse = AppResponse.ok(order);
        return orderAppResponse;
    }
}


