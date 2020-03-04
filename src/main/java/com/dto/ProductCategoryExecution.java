package com.dto;

import com.entity.ProductCategory;
import com.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    //结果状态
    private int stats;
    //结果状态标识
    private String statsInfo;

    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution(){

    }

    //操作失败时使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum){
        this.stats = stateEnum.getState();
        this.statsInfo = stateEnum.getStateInfo();
    }

    //操作成功时使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum,List<ProductCategory> productCategoryList){
        this.stats = stateEnum.getState();
        this.statsInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;

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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
