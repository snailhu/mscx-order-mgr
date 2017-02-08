package com.digitalchina.mscx.mgr.order.service.Impl;

import com.digitalchina.mscx.mgr.order.dao.ApiOfflineMeetMapper;
import com.digitalchina.mscx.mgr.order.domain.ApiOfflineMeet;
import com.digitalchina.mscx.mgr.order.service.IApiOfflineMeetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lubin on 2017/1/9.
 */
@Service
public class ApiOfflineMeetService implements IApiOfflineMeetService{
     @Autowired
     ApiOfflineMeetMapper offlineMapper;
     @Override
    public List<ApiOfflineMeet> getApiOfflinemeets(Map<String, Object> param) {

        return offlineMapper.selectApiOfflineMeets(param);
    }

    @Override
    public int getApiOfflineMeetCount(Map<String,Object> param){
        return offlineMapper.selectApiOfflineMeetCount(param);
    }

    @Override
    public int  acceptApiOfflineMeetSatus(Map<String,Object> param){
        return offlineMapper.updateApiOfflineMeetSatus(param);
    }
    @Override
    public List<ApiOfflineMeet> getApiOfflinemeetById(Map<String,Object> param){
        return offlineMapper.selectApiOfflineMeets(param);
    }
}
