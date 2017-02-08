package com.digitalchina.mscx.mgr.order.service.Impl;

import com.digitalchina.mscx.mgr.order.dao.RouterRuleMapper;
import com.digitalchina.mscx.mgr.order.domain.RouterRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Snail on 2016/12/11.
 */
@Service
public class RouteRuleService {
    @Autowired
    RouterRuleMapper routerRuleMapper;

    public void insertRouter(RouterRule routerRule) {
        routerRuleMapper.insert(routerRule);
    }
}
