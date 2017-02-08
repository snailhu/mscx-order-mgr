package com.digitalchina.mscx.mgr.order.service.Impl;

import com.digitalchina.common.Constants;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.mscx.mgr.order.dao.OrderDetailMapper;
import com.digitalchina.mscx.mgr.order.dto.SourceIdCountDto;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2017/1/8.
 */
@Service
public class SourceService {


    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    CallUrlConfig callUrlConfig;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    public void doPushStatic(String orderNum) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<SourceIdCountDto> sourceIdCountDtos = orderDetailMapper.getCountSource();
                List<Map> apply = new ArrayList<>();
                List<Map>  appStatistics  = new ArrayList<Map>();
                for (SourceIdCountDto ss : sourceIdCountDtos) {
                    Map<String, Integer> apiMap = new HashMap<>();
                    apiMap.put("sourceId", ss.getResourceId());
                    apiMap.put("userNum", ss.getUserNum());
                    apiMap.put("applyNum", ss.getApplyNum());
                    if (Constants.API_TYPE.equals(ss.getResourceType())) {
                        apply.add(apiMap);
                    }
                    if(Constants.WAI_APP_TYPE.equals(ss.getResourceType())){
                        appStatistics.add(apiMap);
                    }
                }
                System.out.println("**************************************推送统计信息****************************************************");
                String apiResponse =restTemplateUtil.post(callUrlConfig.getApiHost(),callUrlConfig.getApiStatisticsUrl(),apply,null);
                System.out.println(apiResponse+"..........................................");
                String appResponse =restTemplateUtil.post(callUrlConfig.getAppHost(),callUrlConfig.getAppStatisticsUrl(),appStatistics,null);
                System.out.println(appResponse+"..........................................");

            }
        }).start();
    }

}
