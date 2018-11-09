package com.neuedu.sell.service.impl;

import com.neuedu.sell.DTO.CartDTO;
import com.neuedu.sell.DTO.OrderDTO;
import com.neuedu.sell.converter.OrderFormToOrderDTOConverter;
import com.neuedu.sell.converter.OrderMasterToOrderDTOConverter;
import com.neuedu.sell.entity.OrderDetail;
import com.neuedu.sell.entity.OrderMaster;
import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.enums.OrderStatusEnum;
import com.neuedu.sell.enums.PayStatusEnum;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ProductInfoRepository productInfoRepository;
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
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);
        /*4扣库存*/
        List<CartDTO> cartDTOList = new ArrayList<>();/*集合转换*/
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        /*订单不存在*/
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        /*订单详情不存在*/
        if (orderDetailList.size() == 0) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = OrderMasterToOrderDTOConverter.OrderMasterToOrderDTOconverter(orderMaster);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String openId, Pageable pageable) {
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(openId, pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.MasterListToOrderDTOconverter(page.getContent());
        return new PageImpl<>(orderDTOList, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        /*根据orderDTO查询出orderMaster*/
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        /*判断订单状态*/
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*修改订单状态*/
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterRepository.save(orderMaster);
        /*返还库存*/
        List<CartDTO> cartDTOList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        /*集合转换,orderDetailList转为cartDTOList集合*/
        for (OrderDetail orderDetail : orderDetailList) {
            cartDTOList.add(new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity()));
        }
        /*遍历cartDTOList，*/
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if (cartDTO.getProductQuantity() <= 0) {
                throw new SellException(ResultEnum.QUANTITY_NOT_LEGAL);
            }
            //加库存
            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());
            productInfoRepository.save(productInfo);
        }
        /*如果支付，返还前*/

        orderDTO.setBuyerOpenid(orderMaster.getBuyerOpenid());
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        /*1.查询订单*/
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        /*2.判断订单状态*/
        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        /*3.修改状态*/
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        /*4.保存*/
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        /*查订单*/
        OrderMaster orderMaster = orderMasterRepository.findOne(orderDTO.getOrderId());
        /*判断支付状态*/
        if (orderMaster.getPayStatus().equals(PayStatusEnum.PAID.getCode())) {
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        /*修改状态*/
        orderMaster.setPayStatus(PayStatusEnum.PAID.getCode());
        /*保存*/
        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        /*商家findAll方法*/
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        /*orderMasterList转orderDTOList*/
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTOConverter.MasterListToOrderDTOconverter(orderMasterPage.getContent());
        /*返回page类型数据*/
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
