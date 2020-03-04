package com.dao;

import com.BaseTest;
import com.entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HeadLIneDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testqueryHeadLine(){

        HeadLine headLine = new HeadLine();
        headLine.setEnableStatus(1);
        List<HeadLine> headLineList = headLineDao.queryHeadLine(headLine);
        for(HeadLine headLine1 : headLineList){
            System.out.println(headLine1.getLineName());
            System.out.println(headLine1.getLineImg());
        }
    }
}
