package com.service.impl;

import com.dao.ProductCategoryDao;
import com.dao.ProductDao;
import com.dto.ProductCategoryExecution;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.enums.ProductCategoryStateEnum;
import com.exceptions.ProductCategoryOperationException;
import com.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public List<ProductCategory> getProductCategory(Shop shop) {
        return productCategoryDao.queryProductCategory(shop);
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {

        if(productCategoryList.size()>0 && productCategoryList!=null){
            try {
                int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("批量添加商品分类失败!");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("errMsg:"+e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId) throws ProductCategoryOperationException{
        if(productCategoryId>0 && shopId>0){
            //先将tb_product中product_category_id置为null
            try{
                int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
                if(effectedNum < 0){
                    throw new ProductCategoryOperationException("商品类别更新失败！");
                }


            }catch (Exception e){
                throw new ProductCategoryOperationException("errMsg:"+e.getMessage());
            }
            //再删除商品分类
            try {
                int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
                if (effectedNum <= 0) {
                    throw new ProductCategoryOperationException("删除商品分类失败!");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("errMsg:"+e.getMessage());
            }
        }else{
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }
}
