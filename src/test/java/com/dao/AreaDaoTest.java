package com.dao;

import com.BaseTest;
import com.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void areaDaoTest()
    {
        List<Area> areaList =areaDao.findAll();
        for(Area area:areaList)
        {
            System.out.println(area.getAreaName());
        }
    }
}
