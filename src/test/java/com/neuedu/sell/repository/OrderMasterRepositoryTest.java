package com.neuedu.sell.repository;

import com.neuedu.sell.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
private static final String OPENID="openid1";
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Test
    public void saveTest(){
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("123456789");
        orderMaster.setBuyerAddress("收货地址1");
        orderMaster.setBuyerName("收货人姓名1");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerPhone("15232109667");
        orderMaster.setOrderAmount(new BigDecimal(7.5));
        orderMasterRepository.save(orderMaster);
    }
       @Test
       public void findByBuyerOpenidTest(){
           Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(OPENID, new PageRequest(0, 2));
           for (OrderMaster orderMaster : page.getContent()) {
               System.out.println(orderMaster);
           }
       }}