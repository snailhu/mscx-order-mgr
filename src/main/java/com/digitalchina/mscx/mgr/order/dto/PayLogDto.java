package com.digitalchina.mscx.mgr.order.dto;

/**
 * Created by Snail on 2016/12/2.
 */
public class PayLogDto
{
     String  orderNum;
     String userId;
     double orderMoney;
     double remainderMoney;
     double payMoney;
     String payWay;
     double nowPay;
     String payDate;
     String  payType;
     int payStatus;
     String payer;
    String payChannel;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(double orderMoney) {
        this.orderMoney = orderMoney;
    }

    public double getRemainderMoney() {
        return remainderMoney;
    }

    public void setRemainderMoney(double remainderMoney) {
        this.remainderMoney = remainderMoney;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayWay() {
        return payWay;
    }

    public double getNowPay() {
        return nowPay;
    }

    public void setNowPay(double nowPay) {
        this.nowPay = nowPay;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
