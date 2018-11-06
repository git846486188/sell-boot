package com.neuedu.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neuedu.sell.enums.ProductStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodsVO {
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private  String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private  String productDescription;
    @JsonProperty("icon")
    private  String productIcon;
}
