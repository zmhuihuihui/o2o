package com.service;

import com.dto.ImageHolder;
import com.dto.ProductExecution;
import com.entity.Product;
import com.exceptions.ProductOperationException;

import java.io.InputStream;
import java.util.List;

public interface ProductService {


    /**
     * 添加商品
     * @param product
     * @param thumbnail 缩略图，ImageHolder封装输入流和名字
     * @param productImgList 详情图
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * Id查询商品
     * @param productId
     * @return
     */
    Product getProductById(Long productId);

    /**
     * 更新商品
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgList) throws ProductOperationException;

    /**
     * 查询商品列表
     * @param product 查询条件
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product product,int pageIndex,int pageSize);



}
