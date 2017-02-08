package com.digitalchina.mscx.mgr.order.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digitalchina.mscx.mgr.order.dao.OrderInfoMapper;
import com.digitalchina.mscx.mgr.order.dao.PayLogMapper;
import com.digitalchina.mscx.mgr.order.domain.OrderInfo;
import com.digitalchina.mscx.mgr.order.domain.PayLog;
import com.digitalchina.mscx.mgr.order.dto.PayLogDto;
import com.digitalchina.mscx.mgr.order.service.IPayLogService;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/2.
 */
@Service
public class PayLogService implements IPayLogService {

    @Autowired
    PayLogMapper payLogMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Override
    public void insertPayLog(PayLogDto pdto) {
        PayLog payLog = new PayLog();
        OrderInfo orderInfo = orderInfoMapper.findByOrderNum(pdto.getOrderNum());
        payLog.setArea(orderInfo.getArea());
        payLog.setCreatedBy(pdto.getCreatedBy());
        payLog.setCreatedTime(new Date());
        payLog.setPayChannel(pdto.getPayChannel());
        payLog.setPayCount(pdto.getPayMoney());
        payLog.setPayStatus(pdto.getPayStatus());
        payLog.setPayer(pdto.getPayer());
        payLog.setOrderNum(pdto.getOrderNum());
        payLog.setPayType(pdto.getPayType());
        payLogMapper.insert(payLog);
    }

    @Override
    public List<PayLog> getPayLogByconf(Map<String, Object> confMap) {
        return payLogMapper.findByConf(confMap);
    }

    @Override
    public void insertPayLog(PayLog payLog) {

        payLogMapper.insert(payLog);
    }

    @Override
    public int getPayLogListCount(Map<String, Object> confMap) {
        return payLogMapper.findPayLogListCount(confMap) ;
    }
}
