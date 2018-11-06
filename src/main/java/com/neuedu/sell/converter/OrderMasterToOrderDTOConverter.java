package com.neuedu.sell.converter;

import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderMasterToOrderDTOConverter {
    public static OrderDTO OrderMasterToOrderDTOconverter(OrderMaster orderMaster){
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return  orderDTO;
    }
    public static List<OrderDTO> MasterListToOrderDTOconverter(List<OrderMaster> orderMasterList){
        List<OrderDTO>  orderDTOList=new ArrayList<>();
        for (OrderMaster orderMaster : orderMasterList) {
            orderDTOList.add(OrderMasterToOrderDTOconverter(orderMaster));
        }
        return orderDTOList;
    }
}
