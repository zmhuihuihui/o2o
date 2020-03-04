package com.controller.frontend;

import com.dto.ShopExecution;
import com.entity.Area;
import com.entity.Shop;
import com.entity.ShopCategory;
import com.enums.ShopStateEnum;
import com.service.AreaService;
import com.service.ShopCategoeyService;
import com.service.ShopService;
import com.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {

    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoeyService shopCategoeyService;
    @Autowired
    private AreaService areaService;

    /**
     * 获取店铺分类，一级或二级目录
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopcategoryandareapageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopCategoryAndAreaPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        List<ShopCategory> shopCategoryList = null;
        Long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        if (parentId > 0) {
            ShopCategory shopCategory = new ShopCategory();
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            shopCategory.setParent(parent);
            shopCategoryList = shopCategoeyService.getShopCategory(shopCategory);
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("success", true);
        } else {
            try{
                shopCategoryList = shopCategoeyService.getShopCategory(null);
                modelMap.put("shopCategoryList", shopCategoryList);
                modelMap.put("success", true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg","查询一级目录错误："+e.toString());
                return modelMap;
            }
        }
        try {
            List<Area> areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }


    /**
     * 根据条件查询店铺列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");

        if(pageIndex>-1 && pageSize>-1) {
            //一级店铺类别
            Long parentCategoryId = HttpServletRequestUtil.getLong(request, "parentCategoryId");
            //二级店铺类别
            Long shopCategoryId = HttpServletRequestUtil.getLong(request,"shopCategoryId");
            //区域分类
            Long areaId = HttpServletRequestUtil.getLong(request, "areaId");
            //店铺名字模糊查询
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            ShopCategory shopCategory = null;
            ShopCategory parentCategory = null;
            Area area = null;
            Shop shopCondition = new Shop();

            if (parentCategoryId != null && parentCategoryId > 0) {
                shopCategory = new ShopCategory();
                parentCategory = new ShopCategory();
                parentCategory.setShopCategoryId(parentCategoryId);;
                shopCategory.setParent(parentCategory);
            }
            if (shopCategoryId != null && shopCategoryId > 0) {
                shopCategory = new ShopCategory();
                shopCategory.setShopCategoryId(shopCategoryId);
            }
            if (areaId != null && areaId > 0) {
                area = new Area();
                area.setAreaId(areaId);
            }
            if (shopName != null) {
                shopCondition.setShopName(shopName);
            }
            shopCondition.setShopCategory(shopCategory);
            shopCondition.setArea(area);
            shopCondition.setEnableStatus(1);

            ShopExecution se = shopService.getShopList(shopCondition, pageIndex,pageSize);
            if (se.getStats() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("shopList", se.getShopList());
                modelMap.put("count",se.getCount());
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询店铺列表失败！");
            }
        }
        return modelMap;

    }

}
