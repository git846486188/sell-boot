package com.neuedu.sell.controller;

import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.enums.OrderStatusEnum;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    /*生成订单列表*/
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page" ,defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "5") Integer size){//返回值也可以是String
        ModelAndView model=new ModelAndView("order/list");
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
        model.addObject("orderDTOPage",orderDTOPage );
        model.addObject("currentPage",page );
        return model;
    }
    /*取消订单*/
    @GetMapping("/cancel")
    @Transactional
    public ModelAndView cancel(@RequestParam("orderId") String orderId){
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
            orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        }catch(SellException e){
            ModelAndView errorModel=new ModelAndView();
            /*跳到哪个页面*/
            errorModel.setViewName("common/error");
            errorModel.addObject("msg",e.getMessage());
            errorModel.addObject("url","/sell/seller/order/list");
            return errorModel;
        }
        ModelAndView successModel=new ModelAndView();
        successModel.setViewName("common/success");
        successModel.addObject("msg","取消成功");
        successModel.addObject("url","/sell/seller/order/list");
        return successModel;
    }
    /*订单详情*/
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId){
        OrderDTO orderDTO=new OrderDTO();
        try {
            orderDTO = orderService.findOne(orderId);
        }catch (SellException e){
            ModelAndView errorModel=new ModelAndView();
            errorModel.setViewName("common/error");
            errorModel.addObject("msg" ,e.getMessage());
            errorModel.addObject("url","/sell/seller/order/list");
            return errorModel;
        }
        ModelAndView model=new ModelAndView();
        model.setViewName("order/detail");
        model.addObject("orderDTO",orderDTO);
        return model;
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId){
        try {
            OrderDTO   orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e){
            ModelAndView errorModel=new ModelAndView();
            errorModel.setViewName("common/error");
            errorModel.addObject("msg" ,e.getMessage());
            errorModel.addObject("url","/sell/seller/order/list");
            return errorModel;
        }
        ModelAndView successModel=new ModelAndView();
        successModel.setViewName("common/success");
        successModel.addObject("msg",ResultEnum.ORDER_FINISH.getMessage());
        successModel.addObject("url","/sell/seller/order/list");
        return successModel;
    }
}
