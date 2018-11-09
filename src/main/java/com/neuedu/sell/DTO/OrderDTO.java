package com.neuedu.sell.DTO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.enums.OrderStatusEnum;
import com.neuedu.sell.enums.PayStatusEnum;
import com.neuedu.sell.utill.StatusEnumUtil;
import com.neuedu.sell.utill.serializer.Data2LongSerializer;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private String orderId;
    /*买家姓名*/
    private String buyerName;
    /*买家电话*/
    private String buyerPhone;
    /*收货地址*/
    private String buyerAddress;
    /*用户的openid*/
    private  String buyerOpenid;
    /*订单总金额*/
    private BigDecimal orderAmount;
    /*订单状态 0为新下单*/
    private  Integer orderStatus=OrderStatusEnum.NEW.getCode();
    /*支付状态，0未支付，1已支付*/
    private Integer payStatus= PayStatusEnum.NOT_PAY.getCode();
    /*创建时间*/
    @JsonSerialize(using = Data2LongSerializer.class)
    private Date createTime;
    /*修改时间*/
    @JsonSerialize(using = Data2LongSerializer.class)
    private  Date updateTime;
    /*订单信息表的集合*/
    private List<OrderDetail> orderDetailList;
    /*获取订单状态*/
    public  OrderStatusEnum getOrderStatusEnum(){
        /*根据OrderStatus的值来返回OrderStatusEnum*/

        return StatusEnumUtil.getEnumByCode(orderStatus,OrderStatusEnum.class);
    }
    /*获取支付状态*/
    public  PayStatusEnum getPayStatusEnum(){
        return StatusEnumUtil.getEnumByCode(payStatus,PayStatusEnum.class);
    }
}
