package com.digitalchina.mscx.mgr.order.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.mscx.mgr.order.domain.PayLog;
import com.digitalchina.mscx.mgr.order.dto.PayLogDto;

/**
 * Created by Snail on 2016/12/2.
 */
public interface IPayLogService {

   void insertPayLog(PayLogDto payLogDto);

    List<PayLog> getPayLogByconf(Map<String,Object> confMap);

    public void insertPayLog(PayLog payLog);

    int  getPayLogListCount(Map<String, Object> confMap);

}