package com.neuedu.sell.service.impl;

import com.neuedu.sell.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoServiceImpl pidao;
    @Test
    public void  findByUpAllTest() {
        for (ProductInfo productInfo : pidao.findByUpAll()) {
            System.out.println(productInfo);
        }
    }
    @Test
    public void findAll(){
        Pageable pageable=new PageRequest(0,2);
        for (ProductInfo productInfo : pidao.findAll(pageable)) {
            System.out.println(productInfo);
        }
    }
}