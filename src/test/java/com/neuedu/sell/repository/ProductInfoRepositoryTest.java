package com.neuedu.sell.repository;

import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.enums.ProductStatusEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest

public class ProductInfoRepositoryTest {
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Test
    public void saveTest(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("654321");
        productInfo.setProductName("小螃蟹");
        productInfo.setCategoryType(0);
        productInfo.setProductDescription("不知道咋吃");
        productInfo.setProductIcon("http://www.xxx");
        productInfo.setProductStock(100);
        productInfo.setProductPrice(new BigDecimal(1.1));
        productInfoRepository.save(productInfo);
    }
    @Test
    public void findAllTest(){
        for (ProductInfo productInfo : productInfoRepository.findAll()) {
            System.out.println(productInfo);
        }

    }
    @Test
    public void findByProductStatus(){
        for (ProductInfo productInfo : productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode())) {
            System.out.println(productInfo);
        }
    }

}