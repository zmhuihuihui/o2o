package com.dao;

import com.entity.ProductImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductImgDao {

    int batchInsertProductImg(@Param("productImgList") List<ProductImg> productImgList);

    int deleteProductImgById(Long productId);

    List<ProductImg> queryProductImgList(@Param("productId") Long productId);
}
