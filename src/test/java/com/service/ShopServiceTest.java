package com.service;

import com.BaseTest;
import com.dto.ImageHolder;
import com.dto.ShopExecution;
import com.entity.Area;
import com.entity.PersonInfo;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.enums.ShopStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;


    @Test
    public void testShopService() throws Exception
    {
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
        shop.setShopName("测试店铺2");
        shop.setShopDesc("test2");
        shop.setShopAddr("test2");
        shop.setPhone("test2");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");

        File shopImg = new File("/Users/zmh/Pictures/xiaohuangren.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
        ShopExecution shopExecution = shopService.addShop(shop,imageHolder);
        assertEquals(ShopStateEnum.CHECK.getState(),shopExecution.getStats());
    }

    @Test
    public void testmodifyShop() throws Exception{
        Shop shop = shopService.getShopById(17L);
        shop.setShopName("修改后测试店铺1");
        File shopImg = new File("/Users/zmh/Pictures/xiaohuangren.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
        ShopExecution shopExecution = shopService.modifyShop(shop,imageHolder);
        System.out.println(shopExecution.getShop().getShopImg());
        assertEquals(ShopStateEnum.SUCCESS.getState(),shopExecution.getStats());
    }

    @Test
    public void testgetShopList(){
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        area.setAreaId(1L);
        owner.setUserId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        ShopExecution sc = shopService.getShopList(shop,1,2);
        List<Shop> shopList = sc.getShopList();
        for(Shop shop1:shopList){
            System.out.println(shop1.getShopName());
        }

    }


}
