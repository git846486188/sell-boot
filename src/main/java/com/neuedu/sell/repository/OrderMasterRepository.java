package com.neuedu.sell.repository;

import com.neuedu.sell.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {
    //Jpa方法名有规则，必须和实体类定义的buyerOpenid一致;
    Page<OrderMaster> findByBuyerOpenid(String OpenId, Pageable pageable);
}
