package com.neuedu.sell.service.impl;

import com.neuedu.sell.entity.Category;
import com.neuedu.sell.repository.ProductCategoryRepository;
import com.neuedu.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service//将类定义成一个bean（实例化放入工厂中）
public class CategoryServiceImpl implements CategoryService {
    @Autowired //注入对象
    ProductCategoryRepository productCategoryRepository;
    @Override
    public List<Category> findByAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public Category findByOne(Integer categoryId) {
        return productCategoryRepository.findOne(categoryId);
    }

    @Override
    public List<Category> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public Category save(Category category) {
        return productCategoryRepository.save(category);
    }
}
