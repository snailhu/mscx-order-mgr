package com.digitalchina.mscx.mgr.order.dto;

import java.util.List;

import com.digitalchina.mscx.mgr.order.domain.PayLog;

/**
 * Created by Snail on 2016/12/4.
 */
public class PayLogListInfo {
    String success;
    List<PayLog> payLogList;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<PayLog> getPayLogList() {
        return payLogList;
    }

    public void setPayLogList(List<PayLog> payLogList) {
        this.payLogList = payLogList;
    }
}
