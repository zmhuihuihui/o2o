package com.controller.frontend;

import com.entity.HeadLine;
import com.entity.ShopCategory;
import com.service.HeadLineService;
import com.service.ShopCategoeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;

    @Autowired
    private ShopCategoeyService shopCategoeyService;

    /**
     * 初始化前端主页，获取头条列表、店铺分类列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listmainpageinfo" ,method = {RequestMethod.GET})
    @ResponseBody
    private Map<String,Object> listMainPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = null;
        List<HeadLine> headLineList = null;
        //获取店铺分类一级列表
        try{
            shopCategoryList = shopCategoeyService.getShopCategory(null);
            modelMap.put("shopCategoryList",shopCategoryList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg","获取店铺一级分类失败！"+e.getMessage());
            return modelMap;
        }
        //获取头条列表
        try{
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            //获取状态为1的可用头条
            headLineList = headLineService.getHeadLineList(headLine);
            modelMap.put("headLineList",headLineList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg","获取头条列表失败"+e.getMessage());
            return modelMap;
        }
        return modelMap;
    }




}
