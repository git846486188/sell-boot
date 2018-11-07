package com.neuedu.sell.service.impl;

import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.service.BuyerService;
import com.neuedu.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service//自动注入到spring容器中
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO= orderService.findOne(orderId);
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;
    }

    @Override
    @Transactional /*先取消再判断，所以加事物*/
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=new OrderDTO();

        orderDTO.setOrderId(orderId);
        OrderDTO result = orderService.cancel(orderDTO);
        if (!result.getBuyerOpenid().equalsIgnoreCase(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return result;
    }
}
