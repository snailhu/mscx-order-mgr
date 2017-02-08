package com.digitalchina.mscx.mgr.order.dto;

import java.util.List;

import com.digitalchina.mscx.mgr.order.domain.OrderInfo;

/**
 * Created by Snail on 2016/12/3.
 */
public class OrderListInfo {
    String success;
    List<OrderInfo> info;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<OrderInfo> getInfo() {
        return info;
    }

    public void setInfo(List<OrderInfo> info) {
        this.info = info;
    }
}
