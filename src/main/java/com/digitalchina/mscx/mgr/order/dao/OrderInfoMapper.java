package com.digitalchina.mscx.mgr.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
import com.digitalchina.mscx.mgr.order.domain.OrderInfoExample;

@Mapper
public interface OrderInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    void updateOrderStatus(OrderInfo orderInfo);

    OrderInfo findByOrderNum(String orderNum);

    List<OrderInfo> getAllByCondition(Map<String,Object> confmap);

    List<OrderInfo> getAll();

    List<OrderInfo> findByConf(Map<String,Object> confmap);

    Integer countByConf(Map<String,Object> confmap);
}