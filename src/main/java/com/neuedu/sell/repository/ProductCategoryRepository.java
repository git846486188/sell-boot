package com.neuedu.sell.repository;

import com.neuedu.sell.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<Category,Integer> {
    List<Category> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
