package com.dao;

import com.entity.Area;


import java.util.List;

public interface AreaDao {

    /**
     * 查询所有
     * @return
     */
    List<Area> findAll();
}
