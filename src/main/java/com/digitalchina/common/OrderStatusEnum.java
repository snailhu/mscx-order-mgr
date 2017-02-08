package com.digitalchina.common;

/**
 * Created by Snail on 2016/12/1.
 */
public enum OrderStatusEnum {

    NOT_PAY("未支付" ,1), PAYING("支付中", 2), PAY_SUCCESS("已付款", 3), PAR_FAIL("付款失败", 4);


    private String name ;
    private int index ;

    private OrderStatusEnum(String name , int index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}

