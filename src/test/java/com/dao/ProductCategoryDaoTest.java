package com.dao;

import com.BaseTest;
import com.entity.ProductCategory;
import com.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testqueryProductCategory(){
        Shop shop = new Shop();
        shop.setShopId(1L);
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(shop);
        for(ProductCategory productCategory:productCategoryList){
            System.out.println(productCategory.getProductCategoryName());
        }

    }

    @Test
    public void testbatchInsertProductCategory(){
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setCreateTime(new Date());
        productCategory1.setPriority(3);
        productCategory1.setShopId(1L);
        productCategory1.setProductCategoryName("测试商品3");
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setCreateTime(new Date());
        productCategory2.setPriority(4);
        productCategory2.setShopId(1L);
        productCategory2.setProductCategoryName("测试商品4");

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);

        int effetedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
        assertEquals(2,effetedNum);
    }

    @Test
    public void testdeleteProductCategory(){
        int effectedNum = productCategoryDao.deleteProductCategory(8L,1L);
        assertEquals(1,effectedNum);
    }
}
