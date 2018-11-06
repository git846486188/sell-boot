package com.neuedu.sell.service;

import com.neuedu.sell.DTO.CartDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    /**
     * 查询在架商品
     */
    List<ProductInfo> findByUpAll();

    /**
     * 分页查询所有
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 根据id查询
     */
    ProductInfo findOne(String productInfoId);

    /**
     * 更新、添加
     */
    ProductInfo save(ProductInfo productInfo);
    /**
     * 扣库存
     */
    void decreaseStock(List<CartDTO> cartDTOList);


}
