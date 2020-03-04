package com.dao;

import com.BaseTest;
import com.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void shopCategoryDaoTest()
    {
        /*List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(1,shopCategoryList.size());*/
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        for(ShopCategory shopCategory : shopCategoryList){
            System.out.println(shopCategory.getShopCategoryName());
        }
    }
}
