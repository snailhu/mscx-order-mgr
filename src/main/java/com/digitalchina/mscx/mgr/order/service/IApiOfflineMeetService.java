package com.digitalchina.mscx.mgr.order.service;

import com.digitalchina.mscx.mgr.order.domain.ApiOfflineMeet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * API服务信息服务类
 */
@Service
public interface IApiOfflineMeetService {

    public List<ApiOfflineMeet> getApiOfflinemeets(Map<String,Object> param);
    public int getApiOfflineMeetCount(Map<String,Object> param);
    public int  acceptApiOfflineMeetSatus(Map<String,Object> param);
    public List<ApiOfflineMeet> getApiOfflinemeetById(Map<String,Object> param);
}
