package com.neuedu.sell.service;

import com.neuedu.sell.entity.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查找所有
     */
    List<Category> findByAll();
    /**
     * id查找
     */
    Category findByOne(Integer id);
    /**
     * 通过类目编号集合来查找
     */
    List<Category> findByCategoryTypeIn(List<Integer> categoryTypeList);
    /**
     * 增加/修改
     */
    Category save(Category category);
}

