package com.neuedu.sell.service.impl;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.neuedu.sell.DTO.CartDTO;
import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.OrderMaster;
import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.enums.ResultEnum;
import com.neuedu.sell.exception.SellException;
import com.neuedu.sell.repository.OrderDetailRepository;
import com.neuedu.sell.repository.OrderMasterRepository;
import com.neuedu.sell.repository.ProductInfoRepository;
import com.neuedu.sell.service.OrderService;
import com.neuedu.sell.service.ProductInfoService;
import com.neuedu.sell.utill.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.beans.Beans;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private ProductInfoService productInfoService;

    @Override
    /*创建订单*/
    @Transactional/*事物*/
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);  /*创建总价对象*/
        String orderId = KeyUtils.generateUniqueKey(); /*生成id*/
        /*1查询商品*/
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo ProductInfo = productInfoService.findOne(orderDetail.getProductId());
            if (ProductInfo == null) {
                /*当商品不存在,抛异常*/
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /*2计算总价*/
            orderAmount = orderAmount.add(ProductInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
            /*3.2 商品详情入库 一个订单有多个商品*/
            /*设置订单id*/
            orderDetail.setOrderId(orderId);
            /*设置此数据id*/
            orderDetail.setDetailId(KeyUtils.generateUniqueKey());
            //复制商品信息
            BeanUtils.copyProperties(ProductInfo, orderDetail);
            //商品存入数据库
            orderDetailRepository.save(orderDetail);
        }
        /*3数据入库*/
        /*3.1订单主表入库   一个订单里可能有多个商品*/
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);
        /*4扣库存*/
        List<CartDTO> cartDTOList=new ArrayList<>();/*集合转换*/
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            cartDTOList.add(new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity()));
        }
        productInfoService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String openId, Package pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
