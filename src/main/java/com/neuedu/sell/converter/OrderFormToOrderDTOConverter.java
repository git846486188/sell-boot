package com.neuedu.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.form.OrderForm;
import com.sun.org.apache.xml.internal.dtm.ref.sax2dtm.SAX2DTM2;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderFormToOrderDTOConverter {
    public static OrderDTO OrderFormToOrderDTO(OrderForm orderForm){
        Gson gson=new Gson();
        OrderDTO orderDTO=new OrderDTO();
        /*BeanUtils.copyProperties(orderForm,orderDTO);两个字段名不一样，不能这样用*/
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        /*将orderForm中的items字符串，转化为detail对象*/
        List<OrderDetail> orderDetailList=null;
        try {
            orderDetailList=gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
