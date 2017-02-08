package com.digitalchina.mscx.mgr.order.dto;

/**
 * Created by Snail on 2016/11/30.
 */
public class OrderInfoDto {

    long orderNum;

    String userName;

    double totalCash;

    double payCash;

    String orderDate;

    String  orderType;

    String orderStatus;

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void setTotalCash(double totalCash) {
        this.totalCash = totalCash;
    }

    public double getPayCash() {
        return payCash;
    }

    public void setPayCash(double payCash) {
        this.payCash = payCash;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
