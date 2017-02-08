package com.digitalchina.mscx.mgr.order.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.mscx.mgr.order.domain.OrderDetail;
import com.digitalchina.mscx.mgr.order.domain.OrderDetailExample;
import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
import com.digitalchina.mscx.mgr.order.dto.OrderDetailDto;
import com.digitalchina.mscx.mgr.order.dto.SourceIdCountDto;

/**
 * Created by Snail on 2016/11/30.
 */
public interface IOrderService {
    public List<OrderInfo> getOrderList(Map<String,Object> confmap);

    public int getOrderListCount(Map<String,Object> confmap);

    List<OrderInfo> finaAll();

    Boolean changeOrderStatus(String orderNum,Integer orderStatus,Double havePay);

    public void insertOrderInfo(OrderInfo orderInfo);
    public void insertOrderDetail(OrderDetail orderDetail);


    public List<OrderDetailDto> getOrderDetailByOrderId(Integer orderId);

    // 获取统计信息
    public List<SourceIdCountDto> getCountSource();
}
