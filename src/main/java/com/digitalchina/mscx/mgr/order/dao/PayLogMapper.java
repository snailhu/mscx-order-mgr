package com.digitalchina.mscx.mgr.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
import com.digitalchina.mscx.mgr.order.domain.PayLog;
import com.digitalchina.mscx.mgr.order.domain.PayLogExample;

@Mapper
public interface PayLogMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(PayLog record);

    List<PayLog> getPayLogByconf(Map<String,Object> confMap);

    List<PayLog> findByConf(Map<String,Object> confmap);

    int findPayLogListCount(Map<String,Object> confmap);

}