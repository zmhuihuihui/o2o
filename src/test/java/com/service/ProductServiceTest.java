package com.service;

import com.BaseTest;
import com.dto.ImageHolder;
import com.dto.ProductExecution;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.enums.ProductStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest  extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testaddProduct() throws Exception{
        Product product = new Product();

        Shop shop = new Shop();
        shop.setShopId(17L);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);

        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setPriority(4);
        product.setProductName("测试3");
        product.setProductDesc("test3");

        //缩略图
        File thumbnail = new File("/Users/zmh/Pictures/xiaohuangren.jpg");
        InputStream is = new FileInputStream(thumbnail);
        ImageHolder imageHolder = new ImageHolder(thumbnail.getName(),is);

        //详情图
        File productImg1 = new File("/Users/zmh/Pictures/xiaohuangren.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        ImageHolder imageHolder1 = new ImageHolder(productImg1.getName(),is1);
        File productImg2 = new File("/Users/zmh/Pictures/IMG_0583.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        ImageHolder imageHolder2 = new ImageHolder(productImg2.getName(),is2);
        List<ImageHolder> imageHolderList = new ArrayList<>();
        imageHolderList.add(imageHolder1);
        imageHolderList.add(imageHolder2);

        ProductExecution pe = productService.addProduct(product,imageHolder,imageHolderList);
        assertEquals(ProductStateEnum.SUCCESS.getState(),pe.getState());
    }
}

