package com.controller;


import com.entity.Area;
import com.service.AreaService;
import com.service.impl.AreaServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

    Logger logger= LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/area" ,method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> listArea()
    {
        logger.info("---start----");
        Long startTime=System.currentTimeMillis();

        Map<String,Object> map =new HashMap<>();
        List<Area> areaList =areaService.getAreaList();
        try {
            map.put("rows", areaList);
            map.put("title", areaList.size());
        }catch (Exception e){
            e.printStackTrace();
            map.put("success",false);
            map.put("errMsg",e.toString());
        }
        logger.error("test error");
        Long endTime=System.currentTimeMillis();
        logger.debug("costTime:"+(endTime-startTime)+"ms");
        logger.info("---end---");
        return map;
    }

}
