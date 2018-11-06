package com.neuedu.sell.controller;

import com.neuedu.sell.VO.DataVO;
import com.neuedu.sell.VO.FoodsVO;
import com.neuedu.sell.VO.ResultVO;
import com.neuedu.sell.entity.Category;
import com.neuedu.sell.entity.ProductInfo;
import com.neuedu.sell.repository.ProductInfoRepository;
import com.neuedu.sell.service.CategoryService;
import com.neuedu.sell.service.ProductInfoService;
import com.neuedu.sell.utill.ResultVOUtill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class ProductController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO List() {
        //查询在架商品信息
        List<ProductInfo> ProductInfos = productInfoService.findByUpAll();
        //把商品type属性放入一个type集合中，方便查商品类别
        List<Integer> CategoryType = new ArrayList<>();
        for (ProductInfo productInfo : ProductInfos) {
            CategoryType.add(productInfo.getCategoryType());
        }
        //根据商品的类别查类别类
        List<Category> categoryList = categoryService.findByCategoryTypeIn(CategoryType);

        //数据拼接
        //将查询出来的商品类型转为VO需要的格式
        List<DataVO> dataVOList = new ArrayList<>();//创建dataVO/商品类型VO的集合
        for (Category category : categoryList) {
            //dataVO对象
            DataVO dataVO = new DataVO();
            //将实体类抽出VO需要的属性放入dataVO对象中
            BeanUtils.copyProperties(category, dataVO);//将category与dataVO相对应的属性复制过去，但是没foods
            //将商品信息整出VO中foods的模样，
            List<FoodsVO> foodsVOList = new ArrayList<>();
            for (ProductInfo productInfo : ProductInfos) {
                if (productInfo.getCategoryType().equals(category.getCategoryType())) {
                    FoodsVO foodsVO = new FoodsVO();
                    BeanUtils.copyProperties(productInfo, foodsVO);
                    foodsVOList.add(foodsVO);
                }
            }
            dataVO.setFoods(foodsVOList);
            //将准备好的dataVO对象放入VO集合
            dataVOList.add(dataVO);
        }
        return ResultVOUtill.success(dataVOList);
 /*       ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        //将最终拼接好的VO放入结果VO中
        resultVO.setData(dataVOList);
        return resultVO;*/
    }

}
