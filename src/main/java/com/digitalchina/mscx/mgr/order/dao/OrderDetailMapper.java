package com.digitalchina.mscx.mgr.order.dao;

import java.util.List;
import java.util.Map;

import com.digitalchina.mscx.mgr.order.dto.SourceIdCountDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalchina.mscx.mgr.order.domain.OrderDetail;
import com.digitalchina.mscx.mgr.order.domain.OrderDetailExample;

@Mapper
public interface OrderDetailMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(OrderDetail record);

    List<OrderDetail> getAllByCondition(Map<String,Object> confmap);

    List<OrderDetail>  getById(Integer orderId);

    OrderDetail findOrderDetailByDetailId (Integer orderDetailId);

    //获取本次订单支付成功后数据资源的统计
    List<SourceIdCountDto> getCountSource();
}