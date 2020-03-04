package com.service.impl;

import com.dao.ProductDao;
import com.dao.ProductImgDao;
import com.dto.ImageHolder;
import com.dto.ProductExecution;
import com.entity.Product;
import com.entity.ProductImg;
import com.enums.ProductStateEnum;
import com.exceptions.ProductOperationException;
import com.service.ProductService;
import com.util.ImageUtil;
import com.util.PageCalculator;
import com.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null) {
            product.setCreateTime(new Date());
            product.setUpdateTime(new Date());
            product.setEnableStatus(1);
            //给商品添加缩略图
            if(thumbnail!=null){
                addThumbnail(product,thumbnail);
            }
            //添加商品到数据库
            try{
                int effected = productDao.insertProduct(product);
                if(effected <= 0){
                    throw new ProductOperationException("创建商品失败！");
                }
            }catch (Exception e){
                throw new ProductOperationException("inset product errMsg:"+e.getMessage());
            }
            //给商品添加详情图
            if(productImgList!=null && productImgList.size()>0) {
                addProductImg(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }

    }

    @Override
    public Product getProductById(Long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        if(product!=null && product.getProductId()>0){
            try {
                product.setUpdateTime(new Date());
                Product tempProduct = productDao.queryProductById(product.getProductId());
                //是否更新缩略图
                if (thumbnail!=null && thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {

                    if(tempProduct.getImgAddr()!=null){
                        ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                    }
                    addThumbnail(product,thumbnail);
                }
                //是否更新详情图
                if (productImgList != null && productImgList.size() > 0) {
                    deleteProductImgList(product.getProductId());
                    addProductImg(product,productImgList);
                }
                //更新商品
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum<=0){
                    throw new ProductOperationException("更新失败！");
                }else{
                    product = productDao.queryProductById(product.getProductId());
                    return new ProductExecution(ProductStateEnum.SUCCESS,product);
                }
            }catch (ProductOperationException e){
                throw new ProductOperationException("errMsg:"+e.getMessage());
            }
        }else{
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }


    @Override
    public ProductExecution getProductList(Product product, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        ProductExecution pe = new ProductExecution();
        List<Product> productList = productDao.queryProductList(product,rowIndex,pageSize);
        int count = productDao.queryProductCount(product);
        if(productList!=null){
            pe.setProductList(productList);
            pe.setCount(count);
        }else{
            return new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
        return pe;
    }


    /**
     * 商品添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product,ImageHolder thumbnail){
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 商品添加详情图
     * @param product
     * @param productImgHolder
     */
    private  void addProductImg(Product product,List<ImageHolder> productImgHolder){
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        for(ImageHolder imageHolder:productImgHolder){
            String imgAddr = ImageUtil.generateNormalImg(imageHolder,dest);
            ProductImg productImg =new ProductImg();
            productImg.setProductId(product.getProductId());
            productImg.setImgAddr(imgAddr);
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果有详情图则添加
        if(productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if(effectedNum <= 0){
                    throw new ProductOperationException("添加详情图失败！");
                }
            }catch (Exception e){
                throw new ProductOperationException("insert productImg errMsg:"+e.getMessage());
            }
        }
    }

    /**
     * 删除商品详情图
     * @param productId
     */
    private void deleteProductImgList(long productId) {
        // 根据productId获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        // 干掉原来的图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // 删除数据库里原有图片的信息
        productImgDao.deleteProductImgById(productId);
    }
}
