package com.digitalchina.mscx.mgr.order.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.Constants;
import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.utils.DateUtil;
import com.digitalchina.mscx.mgr.order.controller.OrderController;
import com.digitalchina.mscx.mgr.order.dao.OrderDetailMapper;
import com.digitalchina.mscx.mgr.order.dao.OrderInfoMapper;
import com.digitalchina.mscx.mgr.order.dao.RouterRuleMapper;
import com.digitalchina.mscx.mgr.order.domain.OrderDetail;
import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
import com.digitalchina.mscx.mgr.order.domain.RouterRule;
import com.digitalchina.mscx.mgr.order.dto.OrderDetailDto;
import com.digitalchina.mscx.mgr.order.dto.OrderFindConditionDto;
import com.digitalchina.mscx.mgr.order.dto.OrderInfoDto;
import com.digitalchina.mscx.mgr.order.dto.SourceIdCountDto;
import com.digitalchina.mscx.mgr.order.service.IOrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Snail on 2016/12/2.
 */
@Service
public class OrderService implements IOrderService{

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    RouterRuleMapper routerRuleMapper;

    private static final Logger logger = LoggerFactory.getLogger(IOrderService.class);

    @Override
    public List<OrderInfo> getOrderList(Map<String,Object> confmap) {

        return  orderInfoMapper.findByConf(confmap);
//        return  orderInfoMapper.getAllByCondition(confmap);

    }

    @Override
    public int getOrderListCount(Map<String, Object> confmap) {
        return orderInfoMapper.countByConf(confmap);
    }

    @Override
    public List<OrderInfo> finaAll() {
      //  return orderInfoMapper.;
        return orderInfoMapper.getAll();

    }


    @Override
    @Transactional
    public Boolean changeOrderStatus(String orderNum,Integer orderStatus,Double havePay) {
        try{
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderNum(orderNum);
            orderInfo.setOrderStatus(orderStatus);
            orderInfo.setHavePay(havePay);
            orderInfoMapper.updateOrderStatus(orderInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void insertOrderInfo(OrderInfo orderInfo) {
        orderInfoMapper.insert(orderInfo);
    }

    @Override
    public void insertOrderDetail(OrderDetail orderDetail) {
        orderDetailMapper.insert(orderDetail);
    }
    @Override
    public List<OrderDetailDto> getOrderDetailByOrderId(Integer orderId) {

        List<OrderDetail> orderDetails = orderDetailMapper.getById(orderId);
        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        for(OrderDetail orderDetail: orderDetails){
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setCash(orderDetail.getItemCashTotal());
            orderDetailDto.setStartTime(DateUtil.format(orderDetail.getCreatedTime()));
            JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
            if(orderDetail.isFree()){
                if(Constants.API_TYPE.equals(orderDetail.getResourceType())){
                    orderDetailDto.setSourceType("api资源");
                    orderDetailDto.setSourceName(parse.getString("apiName"));
                }
                if(Constants.DATA_TYPE.equals(orderDetail.getResourceType())){
                    orderDetailDto.setSourceName(parse.getString("name"));
                    orderDetailDto.setSourceType("数据资源");}
                if(Constants.WAI_APP_TYPE.equals(orderDetail.getResourceType())){
                    orderDetailDto.setSourceName(parse.getString("appName"));
                    orderDetailDto.setSourceType("app资源");}
                orderDetailDto.setPriceTime("免费");
                orderDetailDto.setUseNum("无次数限制");
                orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),24)));
            }else{
                if(Constants.API_TYPE.equals(orderDetail.getResourceType())){
                    orderDetailDto.setSourceType("api资源");
                    orderDetailDto.setSourceName(parse.getString("apiName"));
                    if(Constants.CHARGE_TYPE_BY_FREQUENCY.equals(parse.getString("chargeMethod"))){
                        orderDetailDto.setPriceTime(parse.getString("chargeCount")+"次/"+parse.getString("price")+"元");
                        orderDetailDto.setUseNum(parse.getString("chargeCount"));
                        if(parse.getString("monthLimit")!=null){
                            orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),parse.getIntValue("monthLimit"))));
                        }else{
                            orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),24)));
                        }
                    }
                    if(Constants.CHARGE_TYPE_BY_TIME.equals(parse.getString("chargeMethod"))){
                        orderDetailDto.setPriceTime(parse.getString("chargeCount")+"月/"+parse.getString("price")+"元");
                        orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),parse.getIntValue("chargeCount"))));
                        if(parse.getString("monthLimit")!=null){
                            orderDetailDto.setUseNum(parse.getString("monthLimit"));
                        }else{
                            orderDetailDto.setUseNum("无次数限制");
                        }
                    }

                }else if(Constants.DATA_TYPE.equals(orderDetail.getResourceType())){
                    orderDetailDto.setSourceName(parse.getString("name"));
                    orderDetailDto.setSourceType("数据资源");
                    orderDetailDto.setSourceName(orderDetail.getResourceName());
                    orderDetailDto.setPriceTime("收费");
                    orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),24)));
                    orderDetailDto.setUseNum("无次数限制");
                }else if(Constants.WAI_APP_TYPE.equals(orderDetail.getResourceType())){
                    orderDetailDto.setSourceType("app资源");
                    orderDetailDto.setSourceName(parse.getString("appName"));
                    if(Constants.CHARGE_TYPE_BY_FREQUENCY.equals(parse.getString("chargeMethod"))){
                        orderDetailDto.setPriceTime(parse.getString("chargeCount")+"次/"+parse.getString("price")+"元");
                        orderDetailDto.setUseNum(parse.getString("chargeCount"));
                        if(parse.getString("invokeLimit")!=null){
                            orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),parse.getIntValue("invokeLimit"))));
                        }else{
                            orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),24)));
                        }
                    }
                    if(Constants.CHARGE_TYPE_BY_TIME.equals(parse.getString("chargeMethod"))){
                        orderDetailDto.setPriceTime(parse.getString("chargeCount")+"月/"+parse.getString("price")+"元");
                        orderDetailDto.setEndTime(DateUtil.format(DateUtil.getAddCountMonth(orderDetail.getCreatedTime(),parse.getIntValue("chargeCount"))));
                        if(parse.getString("invokeLimit")!=null){
                            orderDetailDto.setUseNum(parse.getString("invokeLimit"));
                        }else{
                            orderDetailDto.setUseNum("无次数限制");
                        }
                    }
                }
            }

            orderDetailDtos.add(orderDetailDto);
        }
        return orderDetailDtos;
    }

    @Override
    public List<SourceIdCountDto> getCountSource(){
       return orderDetailMapper.getCountSource();
    }
}
