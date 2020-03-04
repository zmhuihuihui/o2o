package com.service;

import com.dto.ImageHolder;
import com.dto.ShopExecution;
import com.entity.Shop;
import com.exceptions.ShopOperationException;

import java.io.InputStream;

public interface ShopService {

    /**
     * 注册店铺信息
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution addShop(Shop shop, ImageHolder shopImg);

    /**
     * 获取店铺信息
     * @param Id
     * @return
     */
    Shop getShopById(Long Id);

    /**
     * 更新店铺信息
     * @param shop
     * @param shopImg
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder shopImg) throws ShopOperationException;

    /**
     * 获取店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);


}
