//package com.digitalchina;
//
//import com.digitalchina.common.Constants;
//import com.digitalchina.common.utils.DateUtil;
//import com.digitalchina.mscx.mgr.order.dao.OrderDetailMapper;
//import com.digitalchina.mscx.mgr.order.dao.OrderInfoMapper;
//import com.digitalchina.mscx.mgr.order.domain.OrderDetail;
//import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
//import com.digitalchina.mscx.mgr.order.domain.PayLog;
//import com.digitalchina.mscx.mgr.order.domain.RouterRule;
//import com.digitalchina.mscx.mgr.order.dto.SourceIdCountDto;
//import com.digitalchina.mscx.mgr.order.service.IOrderService;
//import com.digitalchina.mscx.mgr.order.service.IPayLogService;
//import com.digitalchina.mscx.mgr.order.service.Impl.OrderService;
//
//import com.digitalchina.mscx.mgr.order.service.Impl.RouteRuleService;
//import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.*;
//
///**
// * Created by Snail on 2016/12/1.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = BootApplication.class)
//@WebAppConfiguration
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
////@AutoConfigureMockMvc
//public class OrderTest {
//
//
//    @Autowired
//    IPayLogService payLogService;
//
//    @Autowired
//    RouteRuleService routeRuleService;
//
//    @Autowired
//    IOrderService orderService;
//
//    @Autowired
//    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;
//    @Test
//    public void insertDb() {
//        Map<Integer, Integer> dataMap = new HashMap<>();
//
//        List<SourceIdCountDto> sourceIdCountDtos = orderService.getCountSource();
//        List<Map> apply = new ArrayList<>();
//        for (SourceIdCountDto ss : sourceIdCountDtos) {
//            if (Constants.API_TYPE.equals(ss.getResourceType())) {
//                Map<String, Integer> apiMap = new HashMap<>();
//                apiMap.put("sourceId", ss.getResourceId());
//                apiMap.put("userNum", ss.getUserNum());
//                apiMap.put("applyNum", ss.getApplyNum());
//                apply.add(apiMap);
//            }
//        }
//        System.out.println("**************************************推送统计信息****************************************************");
//        String response =restTemplateUtil.post(Constants.API_HOST,Constants.API_STATISTICS_URL,apply,null);
//        System.out.println("******线下推送"+response);
//
//    }
//
//
//
//    @Test
//    public void insertRouterRule() {
//        for (int i = 20; i < 73; i++) {
//            RouterRule routerRule = new RouterRule();
//            routerRule.setEffectiveNumber(100);
//            routerRule.setEffectiveTime(new Date());
//            routerRule.setIneffectiveTime(new Date());
//            routerRule.setOrderDetailId(i);
//            routerRule.setSourceId(i);
//            routerRule.setArea("001");
//            routeRuleService.insertRouter(routerRule);
//
//        }
//
////    @Test
////    public void isnertlogDb() {
////        for(int i=0;i<40;i++){
////            PayLog payLog =new PayLog();
////            payLog.setOrderNum("123231243141");
////            payLog.setCreatedBy("snail");
////            payLog.setPayStatus(2);
////            payLog.setPayCount(2.3);
////            payLog.setPayChannel("发票");
////            payLog.setArea("1234");
////            payLog.setCreatedTime(new Date());
////            payLog.setCreateUserId("12");
////
////            payLog.setCreatedTime(new Date());
////            payLog.setPayer("xiaoming"+i);
////            payLog.setPayType("2");
////            payLogService.insertPayLog(payLog);
////
////        }
////
////    }
//
////    @Test
////    public void findByConf() {
////        Map<String ,Object> conditionMap =  new HashMap<String ,Object>();
////        //  conditionMap.put("startTime",DateUtil.changeYMD(startTime));
////        //   conditionMap.put("endTime", DateUtil.changeYMD(endTime));
////
////        conditionMap.put("userName","xiaoming");
////        conditionMap.put("payType","online");
////        conditionMap.put("orderStatus",2);
//////        List<OrderInfo> info = orderInfoMapper.findByConf(conditionMap);
//////        System.out.print(info);
////    }
////
////    @Test
////    public void findByConfP() {
////        Map<String ,Object> conditionMap =  new HashMap<String ,Object>();
////          conditionMap.put("startTime", DateUtil.changeYMD("2016-12-01"));
////           conditionMap.put("endTime", DateUtil.changeYMD("2016-12-06"));
////
////        conditionMap.put("payer","xiaoming");
////        conditionMap.put("payType","2");
////        List<PayLog> info = payLogService.getPayLogByconf(conditionMap);
////        System.out.print(info);
//    }
//
//}
