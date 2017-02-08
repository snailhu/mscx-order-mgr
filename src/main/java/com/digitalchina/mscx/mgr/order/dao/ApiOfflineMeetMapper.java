package com.digitalchina.mscx.mgr.order.dao;

import com.digitalchina.mscx.mgr.order.domain.ApiOfflineMeet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ApiOfflineMeetMapper {
    //获取所有的信息
     List<ApiOfflineMeet> selectApiOfflineMeets(Map param);

    //获取总页数

    int selectApiOfflineMeetCount(Map param);

    //受理

    int updateApiOfflineMeetSatus(Map param);

}