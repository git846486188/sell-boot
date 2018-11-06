package com.neuedu.sell.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品类别实体类
 */
@Entity
@Data
@Table(name="product_category")
public class Category {
    @Id
    @GeneratedValue
    private Integer categoryId;
    /*类别名字*/
    private String categoryName; //@column(name="name")
    /*类别编号*/
    private Integer categoryType;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;
}
