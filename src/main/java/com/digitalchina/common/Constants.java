package com.digitalchina.common;

/**
 * Created by xingding on 2016/10/29.
 */
public class Constants {
    /* 平台代码规范常量 */
    /**
     * 通用成功代码
     */
    public final static String RTN_CODE_SUCCESS = "000000";
    /**
     * 通用错误代码
     */
    public final static String RTN_CODE_FAIL = "999999";

    /**
     * 定制的错误代码
     */
    public final static String RTN_CODE_INVALID_API = "123456";

    public final static String RTN_STATUS_SUCCESS = "OK";
    public final static String RTN_STATUS_ERROR = "ERROR";

    /**
     * 通用错误信息
     */
    public final static String RTN_MESSAGE_ERROR = "请求发生异常";

    public final static int DEFAULT_RESOURCE_USE_TIME =24;

    //微服务类型
    public static final String  WAI_APP_TYPE = "03";
    //api类型
    public static final String API_TYPE = "01";
    //数据类型
    public static final String  DATA_TYPE = "02";

    //套餐按时间
    public static final String  CHARGE_TYPE_BY_TIME = "04";
    //套餐按次数
    public static final String  CHARGE_TYPE_BY_FREQUENCY = "05";
    //套餐免费
    public static final String  CHARGE_TYPE_FREE = "01";
    public static final String  ONLINE_OUTPAY = "1";
    public static final String  ONLINE_INPAY = "2";

    public static final String  PUSH_RULE_URL = "http://mscx-gateway.hanlnk.com:82/gateway-web-1.8.0/addRule";
    public static final String  API_HOST = "mscx-api-api.eastdc.cn:82";
    public static final String  SERVER_API_HOST = "mscx-api-api";
    public static final String  API_STATISTICS_URL= "/statistics/receiveApplyCnt.do";
     //受理状态
    public static final String OFFLINE_MEET_ACCEPT= "1";


}
