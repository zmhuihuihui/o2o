package com.dao;

import com.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao {

    /**
     * 新增店铺
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     */
    int updateShop(Shop shop);

    /**
     * 查询店铺
     */
    Shop queryShopById(Long shopId);

    /**
     * 查询店铺列表，可根据条件：店铺名（模糊），店铺状态，店铺类别，区域Id，owner
     * @param shopCondition 查询条件
     * @param rowIndex 从第几行开始取
     * @param pageSize 返回的行数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    /**
     * 查询店铺总数，可根据条件：店铺名（模糊），店铺状态，店铺类别，区域Id，owner
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);


}
