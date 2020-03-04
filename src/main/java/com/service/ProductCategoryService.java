package com.service;

import com.dto.ProductCategoryExecution;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.exceptions.ProductCategoryOperationException;


import java.util.List;

public interface ProductCategoryService {

    /**
     * 获取商品类别
     * @param shop
     * @return
     */
    List<ProductCategory> getProductCategory(Shop shop);

    /**
     * 批量添加商品类别
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    /**
     * 删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    ProductCategoryExecution deleteProductCategory(Long productCategoryId,Long shopId) throws  ProductCategoryOperationException;
}
