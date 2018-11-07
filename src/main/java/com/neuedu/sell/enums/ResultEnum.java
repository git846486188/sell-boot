package com.neuedu.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(0,"商品不存在"),
    QUANTITY_NOT_LEGAL(1,"数量不合法"),
    STOCK_NOT_ENOUGH(2,"库存不足"),
    ORDER_NOT_EXIST(3,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(4,"订单详情不存在"),
    ORDER_STATUS_ERROR(5,"订单状态不合法"),
    PAY_STATUS_ERROR(6,"支付状态不正确");
    private Integer code;
    private String message;
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
