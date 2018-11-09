package com.neuedu.sell.service;

import com.neuedu.sell.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrderService {
    /*创建订单*/
    OrderDTO create(OrderDTO orderDTO);
    /*根据订单id来查找*/
    OrderDTO findOne(String orderId);
    /*根据openId查找订单列表*/
    Page<OrderDTO> findList(String openId, Pageable pageable) ;
    /*取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);
    /*完结订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /*订单支付*/
    OrderDTO paid(OrderDTO orderDTO);
   /*卖家端使用*/
    Page<OrderDTO> findList( Pageable pageable) ;

}
