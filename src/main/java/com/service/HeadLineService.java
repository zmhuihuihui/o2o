package com.service;

import com.entity.HeadLine;

import java.io.IOException;
import java.util.List;

public interface HeadLineService {

    /**
     * 获取头条列表
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

}
