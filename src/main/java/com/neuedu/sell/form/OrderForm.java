package com.neuedu.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
/*装载前端传过来的信息*/
public class OrderForm {
    @NotEmpty(message = "名字不能为空")/*后端校验，如果为空，输出信息*/
    private String name;
    @NotEmpty(message = "电话不能为空")
    private String phone;
    @NotEmpty(message = "地址不能为空")
    private String address;
    @NotEmpty(message = "openid不能为空")
    private String openid;
    @NotEmpty(message = "商品信息不能为空")
    private String items;

}
