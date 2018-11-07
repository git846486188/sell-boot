package com.neuedu.sell.controller;

import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.VO.ResultVO;
import com.neuedu.sell.converter.OrderFormToOrderDTOConverter;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.form.OrderForm;
import com.neuedu.sell.service.OrderService;
import com.neuedu.sell.service.impl.BuyerServiceImpl;
import com.neuedu.sell.utill.ResultVOUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BuyerServiceImpl buyerServiceImpl;
    @PostMapping("/create")                    /*@Valid 检验OrderForm是否有异常,将结果放入bindingResult*/
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        /*1.检验参数合法性*/

        if (bindingResult.hasErrors()) {
            /*抛异常时，抛出OrderForm注解里我们具体定义的参数异常*/
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        /*2.create参数为orderDTO，orderForm转为orderDTO */
        OrderDTO orderDTO = OrderFormToOrderDTOConverter.OrderFormToOrderDTO(orderForm);
        /*3.调用业务层，创建订单*/
        OrderDTO resultDTO = orderService.create(orderDTO);
        /*4.包装结果*/
        Map<String, String> map = new HashMap<>();

        map.put("orderId", resultDTO.getOrderId());
        System.out.println(ResultVOUtill.success(map));
        return ResultVOUtill.success(map);
    }
    @GetMapping("/list")
    public ResultVO<List<OrderDetail>> List(@RequestParam("openid") String openid,
                                            @RequestParam(value = "page",defaultValue = "0") Integer page,
                                            @RequestParam(value = "size",defaultValue = "10") Integer size){

        if (StringUtils.isEmpty(openid)) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderpage = orderService.findList(openid, pageRequest);
        return ResultVOUtill.success(orderpage.getContent());
    }

    @GetMapping("/detail")
    public ResultVO detail(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        /*查询orderid，不安全，会有横向越权问题*/
        // TODO 会有横向越权问题
        //OrderDTO orderDTO= orderService.findOne(orderId);
        OrderDTO orderDTO = buyerServiceImpl.findOrderOne(openid, orderId);
        return ResultVOUtill.success(orderDTO);
    }
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        /*删除，不安全，*/
       // TODO 会有横向越权问题
       /* OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerOpenid(openid);
        orderDTO.setOrderId(orderId);
        orderService.cancel(orderDTO);*/
        buyerServiceImpl.cancelOrder(openid, orderId);
        return ResultVOUtill.success();
    }
}
