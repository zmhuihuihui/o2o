package com.dao;

import com.BaseTest;
import com.entity.ProductImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testbatchInsertProductImg(){
        ProductImg productImg1 = new ProductImg();
        productImg1.setCreateTime(new Date());
        productImg1.setImgAddr("测试1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setCreateTime(new Date());
        productImg2.setImgAddr("测试2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setPriority(2);
        productImg2.setProductId(1L);

        List<ProductImg> productImgList = new ArrayList<>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2,effectedNum);

    }

    @Test
    public void testqueryProductImgList(){
        List<ProductImg> productImgList = productImgDao.queryProductImgList(1L);
        for(ProductImg productImg:productImgList){
            System.out.println(productImg.getImgDesc());
        }
    }

}
