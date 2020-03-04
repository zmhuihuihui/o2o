package com.dao;

import com.entity.Product;
import com.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {

    int insertProduct(Product product);

    int updateProduct(Product productCondition);

    Product queryProductById(Long productId);

    List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    int queryProductCount(@Param("productCondition") Product productCondition);

    int updateProductCategoryToNull(Long productCategoryId);

}
