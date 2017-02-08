package com.digitalchina.common;

/**
 * Created by Snail on 2016/12/7.
 */
public enum PayTypeEnum {

    CASH("现金" ,"1"), CHEQUE("支票", "2"), EXCHANGE("汇票", "3"), TRANSFE("转账", "4"),OTHER("其他", "5");


    private String name ;
    private String index ;

    private PayTypeEnum(String name , String index ){
        this.name = name ;
        this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
