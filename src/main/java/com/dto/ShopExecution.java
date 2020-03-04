package com.dto;

import com.entity.Shop;
import com.enums.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    //结果状态
    private int stats;
    //结果状态标识
    private String statsInfo;
    //店铺数量
    private int count;
    //操作的店铺（增删改）
    private Shop shop;
    //shop列表（查询）
    private List<Shop> shopList;

    public ShopExecution(){

    }

    //操作失败使用的构造器
    public ShopExecution(ShopStateEnum statsEnum) {
        this.stats=statsEnum.getState();
        this.statsInfo=statsEnum.getStateInfo();
    }

    //操作成功使用的构造器
    public ShopExecution(ShopStateEnum statsEnum, Shop shop){
        this.stats=statsEnum.getState();
        this.statsInfo=statsEnum.getStateInfo();
        this.shop=shop;
    }

    //操作成功使用的构造器
    public ShopExecution(ShopStateEnum statsEnum, List<Shop> shopList){
        this.stats=statsEnum.getState();
        this.statsInfo=statsEnum.getStateInfo();
        this.shopList=shopList;
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
    }

    public String getStatsInfo() {
        return statsInfo;
    }

    public void setStatsInfo(String statsInfo) {
        this.statsInfo = statsInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
