package com.dao;

import com.BaseTest;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.ProductImg;
import com.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Test
    public void testinsertProduct(){
        Product product  = new Product();
        product.setCreateTime(new Date());
        product.setProductName("测试商品1");
        product.setProductDesc("测试1");
        product.setPriority(1);
        product.setEnableStatus(0);
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(2L);
        product.setProductCategory(productCategory);
        product.setShop(shop);
        int effectedNum = productDao.insertProduct(product);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testqueryProductById()
    {
        Product product = productDao.queryProductById(10L);
        System.out.println(product.getProductName());
        System.out.println(product.getProductDesc());
        System.out.println(product.getEnableStatus());
        System.out.println(product.getShop().getShopId());
        System.out.println(product.getProductCategory().getProductCategoryId());
        for(ProductImg productImg:product.getProductImgList()){
            System.out.println(productImg.getImgAddr());
        }
    }

    @Test
    public void testupdateProduct(){
        Product product = new Product();
        product.setProductId(8L);
        product.setProductName("芬达");
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testqueryProductListAndCount(){
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);

        List<Product> productList = productDao.queryProductList(product,0,10);
        System.out.println(productDao.queryProductCount(product));
        for(Product product1 : productList){
            System.out.println(product1.getProductName());
        }
    }
}
