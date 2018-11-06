package com.neuedu.sell.DTO;

import lombok.Data;

@Data
public class CartDTO {
    /*OrderDetail表两个字段*/
    /*商品ID*/
    private String productId;
    /*商品数量*/
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
