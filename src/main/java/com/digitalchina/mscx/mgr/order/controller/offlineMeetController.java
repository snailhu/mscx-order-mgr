package com.digitalchina.mscx.mgr.order.controller;
import com.digitalchina.common.Constants;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.pagination.Page;
import com.digitalchina.common.pagination.PaginationUtils;
import com.digitalchina.mscx.mgr.order.domain.ApiOfflineMeet;
import com.digitalchina.mscx.mgr.order.service.IApiOfflineMeetService;
import com.digitalchina.platform.security.context.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * by lubin
 */
@Controller
public class offlineMeetController {

    @Autowired
    IApiOfflineMeetService iOfflineService;

    /**
     * 线下洽谈首页
     * @return
     */
    @RequestMapping(value = "/offlineMeet.do", method = RequestMethod.GET)
    public String manageOrder(){
        return "/order/offlineMeetmain";
    }


    /**
     * 根据提交获取线下洽谈列表
     * @param pageSize
     * @param page
     * @param applyUser
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOfflineMeets.do", method = RequestMethod.GET)
    public String getOfflineMeets(@RequestParam(required = false, defaultValue = "10") long pageSize, @RequestParam(required = false, defaultValue = "1") long page, @RequestParam(required = false) String applyUser, ModelMap map, HttpServletRequest request
    ){
        Map<String ,Object> param =  new HashMap<String ,Object>();
        param.put("applyUser",applyUser);
        if (applyUser != null ) {
            if(applyUser.contains("%")){
                applyUser = applyUser.replaceAll("\\%", "\\\\%");
            }
            if(applyUser.contains("_")){
                applyUser = applyUser.replaceAll("\\_", "\\\\_");
            }
        }
        param.put("applyUser",applyUser);
        int count = iOfflineService.getApiOfflineMeetCount(param);
        Page pagination = PaginationUtils.getPageParam(count, pageSize, page);
        param.put("startIndex", pagination.getStartIndex());
        param.put("endIndex", pagination.getEndIndex());
        pagination.setUrl(request.getRequestURI());//计算出分页查询时需要使用的索引
        List<ApiOfflineMeet> info = iOfflineService.getApiOfflinemeets(param);
        map.put("page", pagination);
        map.put("offlinMeets", info);
        return "/order/offlineList :: meetList";
    }


    /**
     * 根据提交获取线下洽谈列表
     * @param offlineId
     * @return
     */
    @RequestMapping(value = "/acceptOfflineMeet.do", method = RequestMethod.GET)
    @ResponseBody
    public RtnData acceptOfflineMeet(@RequestParam(required = true) String  offlineId){
        Map<String ,Object> param =  new HashMap<String ,Object>();
        param.put("offlineId",offlineId);
        param.put("updatedBy", UserProxy.getAccount());
        param.put("acceptType", Constants.OFFLINE_MEET_ACCEPT);
         int isAccept = iOfflineService.acceptApiOfflineMeetSatus(param);
         if(isAccept==1){
             return RtnData.ok("受理成功！");
         }
        return RtnData.fail("受理失败！");
    }

    /**
     *详细信息
     * @param offlineId
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/getOfflineDeatils.do", method = RequestMethod.GET)
    public String getOfflineMeets( @RequestParam(required = true) String  offlineId,ModelMap map, HttpServletRequest request){
        Map<String ,Object> param =  new HashMap<String ,Object>();
        param.put("offlineId",offlineId);
        param.put("startIndex",0);
        param.put("endIndex",1);
        List<ApiOfflineMeet> info = iOfflineService.getApiOfflinemeets(param);
        map.put("offlinDetail", info.get(0));
        return "/order/offlineDetail";
    }


}
