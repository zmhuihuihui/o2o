package com.controller.shopadmin;

import com.dto.ProductCategoryExecution;
import com.entity.ProductCategory;
import com.entity.Shop;
import com.enums.ProductCategoryStateEnum;
import com.exceptions.ProductCategoryOperationException;
import com.service.ProductCategoryService;
import com.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 查询用户商品分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductcategory",method = {RequestMethod.GET})
    @ResponseBody
    private Map<String,Object> getProductCategory(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop shop = new Shop();
        shop.setShopId(1L);
        request.getSession().setAttribute("currentShop",shop);
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if(currentShop.getShopId() > 0) {
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategory(currentShop);
            modelMap.put("productCategoryList",productCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","shopId非法");
        }
        return modelMap;

    }

    /**
     * 批量添加商品分类
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproductcategory",method = {RequestMethod.POST})
    @ResponseBody
    private Map<String,Object> addProductCategory(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        for(ProductCategory productCategory:productCategoryList){
            productCategory.setShopId(currentShop.getShopId());
        }
        if(productCategoryList!=null &&productCategoryList.size()>0) {
            try {
                ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
                if (pe.getStats() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStatsInfo());
                }
            }catch (ProductCategoryOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少选择一个商品分类！");
        }
        return  modelMap;
    }

    @RequestMapping(value = "/deleteproductcategory",method = {RequestMethod.POST})
    @ResponseBody
    private Map<String,Object> deleteProductCategory(Long productCategoryId,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        if(productCategoryId>0 && productCategoryId!=null){
            try {
                Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
                ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (pe.getStats() == ProductCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStatsInfo());
                }
            }catch (ProductCategoryOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请至少选择一个商品分类！");
        }
        return  modelMap;
    }

}
