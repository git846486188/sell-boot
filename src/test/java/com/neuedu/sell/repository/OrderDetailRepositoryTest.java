package com.neuedu.sell.repository;

import com.neuedu.sell.entity.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {
     @Autowired
     private  OrderDetailRepository orderDetailRepository;
     @Test
    public void saveTest(){
         OrderDetail orderDetail=new OrderDetail();
         orderDetail.setDetailId("123");
         orderDetail.setOrderId("123456789");
         orderDetail.setProductId("654321");
         orderDetail.setProductName("小螃蟹");
         orderDetail.setProductPrice(new BigDecimal(1.1));
         orderDetail.setProductQuantity(1000);
         orderDetail.setProductIcon("http://www.xxx");
         orderDetailRepository.save(orderDetail);
     }
     @Test
    public void findByOrderId(){
         List<OrderDetail> details = orderDetailRepository.findByOrderId("123456789");
         for (OrderDetail detail : details) {
             System.out.println(detail);
         }
     }
}