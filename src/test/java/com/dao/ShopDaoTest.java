package com.dao;

import com.BaseTest;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    @Test
    public void testinsertShop() {

        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1L);
        shopCategory.setShopCategoryId(1L);

        Shop shop = new Shop();
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试店铺");
        shop.setShopDesc("test");
        shop.setShopAddr("test");
        shop.setPhone("test");
        shop.setShopImg("test");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");

        int effectNum =shopDao.insertShop(shop);
        assertEquals(1,effectNum);
    }

    @Test
    public void testupdateShop() {

        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setUpdateTime(new Date());
        int effectNum =shopDao.updateShop(shop);
        assertEquals(1,effectNum);
    }

    @Test
    public void testqueryShopById(){

        Shop shop = shopDao.queryShopById(18L);
        System.out.println(shop.getShopImg());
        System.out.println(shop.getArea().getAreaName());
        System.out.println(shop.getShopCategory().getShopCategoryName());

    }

    @Test
    public void testqueryShopCount(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shop.setOwner(owner);
        assertEquals(4,shopDao.queryShopCount(shop));
    }

    @Test
    public void testqueryShopList(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        area.setAreaId(1L);
        owner.setUserId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        List<Shop> shopList = shopDao.queryShopList(shop,0,2);
        for(Shop shop1 :shopList)
            System.out.println(shop1.getShopName());
    }

}
