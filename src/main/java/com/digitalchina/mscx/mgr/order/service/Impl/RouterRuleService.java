package com.digitalchina.mscx.mgr.order.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.Constants;
import com.digitalchina.common.GatewayRequestCallback;
import com.digitalchina.common.GatewayResponseExecutor;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.mscx.mgr.order.dao.OrderDetailMapper;
import com.digitalchina.mscx.mgr.order.dao.RouterRuleMapper;
import com.digitalchina.mscx.mgr.order.domain.OrderDetail;
import com.digitalchina.mscx.mgr.order.domain.RouterRule;
import com.digitalchina.mscx.mgr.order.util.DateUtil;
import com.digitalchina.mscx.mgr.order.util.HttpClientUtil;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2017/1/8.
 */
@Service
public class RouterRuleService {
    @Autowired
    RouterRuleMapper routerRuleMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    @Autowired
    CallUrlConfig callUrlConfig;


    public void doRouterRule(String orderNum) {
        //根据订单号去查询资
        List<RouterRule> routerRules = routerRuleMapper.getRouterRuleByOrderNum(orderNum);
        for(RouterRule rr:routerRules){
            OrderDetail orderDetail = orderDetailMapper.findOrderDetailByDetailId(rr.getOrderDetailId());
            this.pushRule(rr,orderDetail);
        }

    }

    public void pushRule(RouterRule rr,OrderDetail orderDetail){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, Object> header = new HashMap<String, Object>();
                Map<String,Object> params = new HashMap<>();
                Map<String,Object> sourceMap = new HashMap<>();
                sourceMap.put("userId",rr.getOrderUserId());
                sourceMap.put("resourceId",rr.getSourceId());
                sourceMap.put("resourceType",orderDetail.getResourceType());
                JSONObject orderDetailParse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
                if(Constants.DATA_TYPE.equals(orderDetail.getResourceType())){
                    sourceMap.put("charRuleType","-1");
                }
                if(orderDetail.getItemCash()==0){
                    sourceMap.put("charRuleType","0");
                }if(Constants.CHARGE_TYPE_BY_FREQUENCY.equals(orderDetailParse.getString("chargeMethod"))){
                    sourceMap.put("charRuleType","2");
                }if(Constants.CHARGE_TYPE_BY_TIME.equals(orderDetailParse.getString("chargeMethod"))){
                    sourceMap.put("charRuleType","1");
                }
                sourceMap.put("startTime",DateUtil.format(rr.getEffectiveTime()));
                sourceMap.put("endTime", DateUtil.format(rr.getIneffectiveTime()));
                sourceMap.put("number",rr.getEffectiveNumber());
                sourceMap.put("orderDetailId",orderDetail.getId());
                List<Map> paramsList = new ArrayList<>();
                paramsList.add(sourceMap);
                String paramsT = "data="+JSON.toJSONString(paramsList);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
                String response = (String) restTemplateUtil.execute(callUrlConfig.getGateWayHost(), callUrlConfig.getPushRuleUrl(), HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());
                JSONArray array = (JSONArray) JSONObject.parse(response);
                array.getJSONObject(0).getString("result");


            }
        }).start();

    }


}
