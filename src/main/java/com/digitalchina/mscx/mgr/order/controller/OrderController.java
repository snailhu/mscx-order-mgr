package com.digitalchina.mscx.mgr.order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.Constants;
import com.digitalchina.mscx.mgr.order.dto.OrderDetailDto;
import com.digitalchina.mscx.mgr.order.service.Impl.RouterRuleService;
import com.digitalchina.mscx.mgr.order.service.Impl.SourceService;
import com.digitalchina.platform.security.context.UserProxy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.pagination.Page;
import com.digitalchina.common.pagination.PaginationUtils;
import com.digitalchina.common.utils.DateUtil;
import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
import com.digitalchina.mscx.mgr.order.dto.OrderListInfo;
import com.digitalchina.mscx.mgr.order.dto.PayLogDto;
import com.digitalchina.mscx.mgr.order.service.IOrderService;
import com.digitalchina.mscx.mgr.order.service.IPayLogService;

/**
 * Created by Snail on 2016/11/30.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IOrderService iOrderService;

    @Autowired
    IPayLogService iPayLogService;

    @Autowired
    RouterRuleService routerRuleService;
    @Autowired
    SourceService sourceService;
    /**
     * 根据条件获取订单
     * @return
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
    public String getOrderLists(
            @RequestParam(required = false, defaultValue = "10") long pageSize,
            @RequestParam(required = false, defaultValue = "1") long page,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String orderNum,
            @RequestParam(required = false) String payType,
            @RequestParam(required = false) Integer orderStatus, ModelMap map, HttpServletRequest request
    ){
        Map<String ,Object> conditionMap =  new HashMap<String ,Object>();
        conditionMap.put("userName",userName);
        conditionMap.put("orderNum",orderNum);
        conditionMap.put("orderType",payType);
        conditionMap.put("orderStatus",orderStatus);
        conditionMap.put("area",UserProxy.getExt1());
        if(!(StringUtils.isEmpty(startTime))&& !(StringUtils.isEmpty(endTime))){
            conditionMap.put("startTime",DateUtil.format(startTime+" 00:00:00"));
            conditionMap.put("endTime", DateUtil.format(endTime+" 23:59:59"));
        }
        int count = iOrderService.getOrderListCount(conditionMap);
        Page pagination = PaginationUtils.getPageParam(count, pageSize, page);
        conditionMap.put("startIndex", pagination.getStartIndex());
        conditionMap.put("endIndex", pagination.getEndIndex());
        pagination.setUrl(request.getRequestURI());//计算出分页查询时需要使用的索引
        List<OrderInfo> info = iOrderService.getOrderList(conditionMap);
        OrderListInfo oinfo = new OrderListInfo();
        map.put("page", pagination);
        map.put("dataInfoMaintains", info);
        return "/order/orderList :: dataList";
    }

    /**
     * 订单管理首页
     * @return
     */
    @RequestMapping(value = "/manageOrder", method = RequestMethod.GET)
    public String manageOrder(){
        return "/order/ordermain";
    }

    /**
     * 付款登记
     * @param orderNum
     * @param payer
     * @param payCount
     * @param payType
     * @param payTime
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/payRecord", method = RequestMethod.POST)
    public RtnData payRecord(
            @RequestParam(required = false) String  orderNum,
            @RequestParam(required = false) String payer,
            @RequestParam(required = false) String payCount,
            @RequestParam(required = false) String payType,
            @RequestParam(required = false) String payTime
         //   @RequestParam(required = false) String payStatus
    ){
       if(StringUtils.isEmpty(orderNum)) {
           return RtnData.ok("error");
       }
       else{
           PayLogDto pdto = new PayLogDto();
           pdto.setNowPay(Double.parseDouble(payCount));
           pdto.setOrderMoney(Double.parseDouble(payCount));
           pdto.setPayDate(payTime);
           pdto.setOrderNum(orderNum);
           pdto.setPayMoney(Double.parseDouble(payCount));
           pdto.setPayWay(payType);
           pdto.setRemainderMoney(Double.parseDouble(payCount));
           pdto.setPayType(Constants.ONLINE_OUTPAY);
           pdto.setCreatedBy(UserProxy.getName());
           pdto.setPayChannel("1");
           pdto.setPayer(payer);
           Boolean isSuccess = iOrderService.changeOrderStatus(orderNum, OrderStatusEnum.PAY_SUCCESS.getIndex(),Double.parseDouble(payCount));
           if(isSuccess){
               iPayLogService.insertPayLog(pdto);
               routerRuleService.doRouterRule(orderNum);
               sourceService.doPushStatic(orderNum);
               return RtnData.ok("ok");
           }else{
               return  RtnData.fail("error");
           }

       }
    }

    //获取订单详情
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    @ResponseBody
    public List<OrderDetailDto> getOrdetDetail(
            @RequestParam(required = true ) String  orderId){

        List<OrderDetailDto> orderDetailDtos = iOrderService.getOrderDetailByOrderId(Integer.parseInt(orderId));
        return orderDetailDtos;
    }



}
