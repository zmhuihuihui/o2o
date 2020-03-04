package com.controller.shopadmin;

import com.dto.ImageHolder;
import com.dto.ProductExecution;
import com.entity.Product;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.enums.ProductStateEnum;
import com.exceptions.ProductCategoryOperationException;
import com.exceptions.ProductOperationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.ProductCategoryService;
import com.service.ProductService;
import com.util.CodeUtil;
import com.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;

    //详情图最大上传数
    private static final int IMAGE_MAX_COUNT = 6;

    @RequestMapping(value = "/addproduct", method = {RequestMethod.POST})
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        //验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }

        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        /**
         * productStr 接收前端json转换的String流
         * mapper 用于解析String流到对应对象
         * CommonsMultipartResolver 用于解析request,获取文件流
         * multipartRequest 用户处理文件流
         */
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest multipartRequest = null;

        try {
            //通过mapper获取商品实体类
            product = mapper.readValue(productStr, Product.class);
            //判断request中是否有图片文件流
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;

                //处理缩略图
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

                //处理详情图(最多六张图)
                for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        productImgList.add(new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream()));
                    } else {
                        break;
                    }
                }

            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空！");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //调用service添加商品
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                }
            } catch (ProductCategoryOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息！");
        }
        return modelMap;
    }


    @RequestMapping(value = "/getproductbyid", method = {RequestMethod.GET})
    @ResponseBody
    private Map<String, Object> getProductById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Long productId = HttpServletRequestUtil.getLong(request, "productId");
        if (productId != null && productId > 0) {
            try {
                Product product = productService.getProductById(productId);
                List<ProductCategory> productCategoryList = productCategoryService.getProductCategory(product.getShop());
                modelMap.put("product", product);
                modelMap.put("productCategoryList", productCategoryList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "商品Id错误！");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyproduct", method = {RequestMethod.POST})
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        if (!statusChange) {
            if (!CodeUtil.checkVerifyCode(request)) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "验证码错误");
                return modelMap;
            }
        }

        List<ImageHolder> productImgList = new ArrayList<>();
        Product product = null;
        ImageHolder thumbnail = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ObjectMapper mapper = new ObjectMapper();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        CommonsMultipartFile thumbnailFile =null;
        try {
            //通过mapper获取商品实体类
            product = mapper.readValue(productStr, Product.class);
            //判断request中是否有图片文件流
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

                //处理缩略图
                thumbnailFile = (CommonsMultipartFile)multipartRequest.getFile("shopImg");
                if(thumbnail!=null) {
                    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
                }
                //处理详情图(最多六张图)
                for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        productImgList.add(new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream()));
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //调用service修改商品
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                }
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息！");
        }
        return modelMap;

    }

    @RequestMapping(value = "/getproductlist", method = {RequestMethod.GET})
    @ResponseBody
    private Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() > 0) {
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            String productName = HttpServletRequestUtil.getString(request, "productName");
            Product product = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            try {
                ProductExecution pe = productService.getProductList(product, pageIndex, pageSize);
                modelMap.put("productList", pe.getProductList());
                modelMap.put("count", pe.getCount());
                modelMap.put("success", true);
            } catch (Exception e) {
                throw new ProductOperationException("errMsg:" + e.getMessage());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;

    }

    /**
     * 封装查询条件
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        // 若有指定类别的要求则添加进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        // 若有商品名模糊查询的要求则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

}
