package com.service.impl;

import com.dao.HeadLineDao;
import com.entity.HeadLine;
import com.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {

    @Autowired
    private HeadLineDao headLineDao;

    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        if(headLineCondition == null){
            throw new IOException("搜索头条条件为空");
        }else {
            return headLineDao.queryHeadLine(headLineCondition);
        }
    }
}
