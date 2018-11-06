package com.neuedu.sell.service.impl;

import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void createTest() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小张");
        orderDTO.setBuyerPhone("110");
        orderDTO.setBuyerOpenid("微信openid");
        orderDTO.setBuyerAddress("天津睿道");
        List<OrderDetail> OrderDetailList = new ArrayList<>();
        OrderDetailList.add(new OrderDetail("123456", 10));
        OrderDetailList.add(new OrderDetail("654321", 10));
        orderDTO.setOrderDetailList(OrderDetailList);
        orderService.create(orderDTO);
    }
}