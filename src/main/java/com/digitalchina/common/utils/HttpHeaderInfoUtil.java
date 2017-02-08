package com.digitalchina.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuyd on 2016/12/3.
 * 获取 门户router请求头中的信息
 */
public class HttpHeaderInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpHeaderInfoUtil.class);

    /**
     * 获取请求头中的userId
     * @param request
     * @return
     */
    static public String getCurrentUserId(HttpServletRequest request){
        JSONObject data = getUserData(request);
        String userId = data.getString("userId");
        return userId;
    }

    /**
     * 获取请求头中的account
     * @param request
     * @return
     */
    static public String getCurrentUserAccount(HttpServletRequest request){
        JSONObject data = getUserData(request);
        String account = data.getString("account");
        return account;
    }

    static public String getArea(HttpServletRequest request){
        JSONObject data = getUserData(request);
        String area = data.getString("area");
        return area;
    }
    static private JSONObject getUserData(HttpServletRequest request){
        String data = request.getHeader("user-digitalchina-data");
        return JSONObject.parseObject(data);
    }

}
