package com.neuedu.sell.service.impl;

import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Test
    public void findOneTest(){
        OrderDTO orderDTO=orderService.findOne("123456789");
        System.out.println(orderDTO);
    }
    @Test
    public void findList(){
        Page<OrderDTO> page = orderService.findList("微信openid", new PageRequest(0, 2));
        for (OrderDTO orderDTO : page.getContent()) {
            System.out.println(orderDTO);
        }
    }
}