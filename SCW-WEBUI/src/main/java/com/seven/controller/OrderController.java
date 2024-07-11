package com.seven.controller;

/**
 * @author: seven
 * @since: 2024/7/11 16:59
 */
import com.seven.response.AppResponse;
import com.seven.service.OrderServiceFeign;
import com.seven.vo.OrderFormInfoSubmitVo;
import com.seven.vo.ReturnPayConfirmVo;
import com.seven.vo.TOrder;
import com.seven.vo.UserRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderServiceFeign orderServiceFeign;

    //保存订单
    @RequestMapping("/order/save")
    public String OrderPay(OrderFormInfoSubmitVo vo, HttpSession session) {

        UserRespVo userResponseVo = (UserRespVo) session.getAttribute("sessionMember");
        if (userResponseVo == null) {
            return "redirect:/login";
        }
        String accessToken = userResponseVo.getAccessToken();
        vo.setAccessToken(accessToken);
        ReturnPayConfirmVo confirmVo = (ReturnPayConfirmVo) session.getAttribute("returnConfirmSession");
        if (confirmVo == null) {
            return "redirect:/login";
        }
        //TODO 注意这三个参数没一个名字是一样的
        vo.setProjectid(confirmVo.getProjectId());
        vo.setReturnid(confirmVo.getId());
        vo.setRtncount(confirmVo.getNum());

        AppResponse<TOrder> order = orderServiceFeign.createOrder(vo);
        TOrder data = order.getData();

        return "/member/minecrowdfunding";
        // return  "redirect:"+result;
    }
}