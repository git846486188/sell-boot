package com.neuedu.sell.repository;

import com.neuedu.sell.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
  @Autowired
    private ProductCategoryRepository productCategoryRepository;
  @Test
    public void findAllTest(){
      List<Category> categoryList = productCategoryRepository.findAll();
      for (Category category : categoryList) {
          System.out.println(category);
      }
  }
  @Test
  public void saveTest(){
    Category category=new Category();
    category.setCategoryName("美女专享");
    category.setCategoryType(2);
    productCategoryRepository.save(category);
  }
  @Test
     public void  findByCategoryTypeInTest(){
     List<Integer> categoryTypeList= Arrays.asList(1,2);
     List<Category> categoryList=productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
      for (Category category : categoryList) {
          System.out.println(category);
      }
  }
}