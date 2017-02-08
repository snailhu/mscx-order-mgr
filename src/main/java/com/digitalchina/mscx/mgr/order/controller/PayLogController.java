package com.digitalchina.mscx.mgr.order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.digitalchina.platform.security.context.UserProxy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.common.RtnData;
import com.digitalchina.common.pagination.Page;
import com.digitalchina.common.pagination.PaginationUtils;
import com.digitalchina.common.utils.DateUtil;
import com.digitalchina.mscx.mgr.order.domain.PayLog;
import com.digitalchina.mscx.mgr.order.service.IPayLogService;

/**
 * Created by Snail on 2016/12/5.
 */

@Controller
@RequestMapping("/paylog")
public class PayLogController {

    @Autowired
    IPayLogService iPayLogService;

    @RequestMapping(value = "/addPayLog", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public RtnData addPayLog(
            @RequestParam(required = false) String orderNum,
            @RequestParam(required = false) String payTime,
            @RequestParam(required = false) String payer,
            @RequestParam(required = false)  String payCount,
            @RequestParam(required = false) String payType
    ){
        try {
            PayLog paylog = new PayLog();
            paylog.setCreatedTime(DateUtil.changeYMD(payTime));
            paylog.setPayer(payer);
            paylog.setPayCount(Double.parseDouble(payCount));
            paylog.setOrderNum(orderNum);
            paylog.setPayType(payType);
            paylog.setPayChannel("1");
            iPayLogService.insertPayLog(paylog);
            return RtnData.ok("success");
        } catch (NumberFormatException e) {
            return RtnData.ok("error");
        }
    }
    
    /**
     * 交易订单查询页
     * @return
     */
    @RequestMapping(value = "/showPayLog", method = RequestMethod.GET)
    public String showPayLog(){
        return "/order/payLogMain";
    }
    
    /**
     * 根据条件获取交易记录
     * @return
     */
    @RequestMapping(value = "/getPayLogList", method = RequestMethod.GET)
    public String getPayLogList(
            @RequestParam(required = false, defaultValue = "10") long pageSize,
            @RequestParam(required = false, defaultValue = "1") long page,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            @RequestParam(required = false) String payer,
            @RequestParam(required = false) String payType,
            ModelMap map, HttpServletRequest request

    ){
        Map<String ,Object> conditionMap =  new HashMap<String ,Object>();
        if(!(StringUtils.isEmpty(startTime))&& !(StringUtils.isEmpty(endTime))){
            conditionMap.put("startTime",DateUtil.format(startTime+" 00:00:00"));
            conditionMap.put("endTime", DateUtil.format(endTime+" 23:59:59"));
        }
        conditionMap.put("payer",payer);
        conditionMap.put("payChannel",payType);
        conditionMap.put("area", UserProxy.getExt1());
        int count = iPayLogService.getPayLogListCount(conditionMap);
        Page pagination = PaginationUtils.getPageParam(count, pageSize, page);
        conditionMap.put("startIndex", pagination.getStartIndex());
        conditionMap.put("endIndex", pagination.getEndIndex());
        pagination.setUrl(request.getRequestURI());//计算出分页查询时需要使用的索引

        List<PayLog> paylogs = iPayLogService.getPayLogByconf(conditionMap);
        map.put("page", pagination);
        map.put("dataInfoMaintains", paylogs);
        return "/order/payLogList :: dataList";
    }
}
