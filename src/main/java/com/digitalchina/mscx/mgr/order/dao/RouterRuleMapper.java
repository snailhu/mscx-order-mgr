package com.digitalchina.mscx.mgr.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.digitalchina.mscx.mgr.order.domain.RouterRule;
import com.digitalchina.mscx.mgr.order.domain.RouterRuleExample;

@Mapper
public interface RouterRuleMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(RouterRule record);

    //根据订单号获取路由规则
    List<RouterRule> getRouterRuleByOrderNum(String orderNum);

    List<RouterRule> getRouterRule(Map<String,Object> confMap);

}