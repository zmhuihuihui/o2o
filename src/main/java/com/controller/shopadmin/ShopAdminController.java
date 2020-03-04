package com.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @RequestMapping(value = "/shopoperation",method = {RequestMethod.GET})
    public String shopOperation()
    {
        return "shop/shopoperation";
    }

    @RequestMapping(value = "/shoplist",method = {RequestMethod.GET})
    public String shopList()
    {
        return "shop/shoplist";
    }

    @RequestMapping(value = "/shopmanagement",method = {RequestMethod.GET})
    public String shopManagement()
    {
        return "shop/shopmanagement";
    }

    @RequestMapping(value = "/productcategorymanagement",method = {RequestMethod.GET})
    public String productCategory(){
        return "shop/productcategorymanagement";
    }

    @RequestMapping(value = "/productoperation",method = {RequestMethod.GET})
    public String productOperation(){
        return "shop/productoperation";
    }

    @RequestMapping(value = "/productmanagement",method = {RequestMethod.GET})
    public String productManagement(){
        return "shop/productmanagement";
    }
}
