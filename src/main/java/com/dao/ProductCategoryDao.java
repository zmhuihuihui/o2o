package com.dao;

import com.entity.ProductCategory;
import com.entity.Shop;
import org.apache.ibatis.annotations.Param;


import java.util.List;

public interface ProductCategoryDao {

    /**
     * 获取商品种类
     * @param shop
     * @return
     */
    List<ProductCategory> queryProductCategory(@Param("shop") Shop shop);

    /**
     * 批量添加商品种类
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(@Param("productCategoryList") List<ProductCategory> productCategoryList);

    /**
     * 删除商品种类
     * @param productCategoryId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") Long productCategoryId,@Param("shopId") Long shopId);
}
