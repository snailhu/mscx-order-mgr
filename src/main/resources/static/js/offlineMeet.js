/**
 * Created by Jackie.Chen on 2016/12/4.
 */

var offlineMeet = function () {
    /**
     * 基础配置
     * @type {string}
     */
    var el = 'body';
    var addLay = null,updateLay = null;
    var loadAreaSize = ['600px', '400px']; //宽高
    var searchObj = {
        applyUser:"",
        pageNum: 1,
        pageSize: 10
    };

    /**
     * 获取信息
     */
    function getOfflineMetts(){
        doSearch();
    }

    /**
     * 查询操作
     */
    function doSearch() {
        var applyUser = $("#applyUser").val();
        searchObj.applyUser = applyUser || '';
        searchObj.pageNum = 1;
        renderPages(true);
    }

    /**
     * 受理操作
     *
     */
   function acceptOffline(){

            var offlineId =$(this).attr('data');

            layer.confirm('您确定受理该资源申请吗？', {
                btn: ['确定','取消']
            }, function(){
                ajaxCommonFun({
                    type: 'GET',
                    data:{
                        'offlineId':offlineId
                    },
                    url: '/acceptOfflineMeet.do',
                    success: function (data) {
                        layer.close();
                        layer.msg(data.result);
                        renderPages(true);
                    }
                });

            }, function(){
                layer.msg('已取消受理');
            });

    }

    /**
     * 详情操作
     */


    function  showDeatil() {
        var offlineId =$(this).attr('data');
        getHtmlByUrl({
            url:"/getOfflineDeatils.do?offlineId="+offlineId,
            success: function (res) {
                $("#meetDetaiTemp").html(res);
                layer.open({
                    type: 1,
                    title: '详情',
                    btn:['关闭'],
                    area: ['720px', '450px'],
                    content: $('#meetDetaiTemp'),
                    btn1: function (index){
                        layer.close(index);
                    }
                });
            }
        });


    }


    /**
     * 重置操作
     */

    function resertOffline() {
         $("#applyUser").val('');
    }
    /*页面初始化和事件绑定*/
    function bindEvents() {
        /*update*/
        $(el).undelegate()
            .delegate('#defineBtn','click',getOfflineMetts)
            .delegate('.accept-resource','click',acceptOffline)
            .delegate('.contact-detail','click',showDeatil)
            .delegate('#defineResetBtn','click',resertOffline)

    }
    
    function renderPages(isFirst) {

        var url =  '/getOfflineMeets.do',isAdd=false;
        if(searchObj.applyUser){
            url = isAdd ? url+'&applyUser='+searchObj.applyUser : url+'?applyUser='+searchObj.applyUser;
            isAdd = true;
        }
        if(searchObj.pageSize){
            url = isAdd ? url+'&pageSize='+searchObj.pageSize : url+'?pageSize='+searchObj.pageSize;
            isAdd = true;
        }
        if(searchObj.pageNum){
            url = isAdd ? url+'&page='+searchObj.pageNum :  url+'?page='+searchObj.pageNum;
            isAdd = true;
        }

        getHtmlByUrl({
            url: url,
            success: function (res) {
                $("#offlineShow").html(res);
                if(isFirst){
                    buildPageArea();
                }
            }
        });
    }

    /*分页*/
    function doChangePage(num) {
         searchObj.pageNum = num;
         renderPages();
    }
    
    function buildPageArea() {
        var pageAllCount = $('#pageAllCount').val(),
            totalPage = Math.ceil(pageAllCount/searchObj.pageSize);
        laypage({
            cont: 'page',
            pages: totalPage,
            totalCount:pageAllCount,
            curr: searchObj.pageNum,
            prev: '上一页', //若不显示，设置false即可
            next: '下一页', //若不显示，设置false即可
            jump: function(obj, first){
                if(!first){
                    doChangePage(obj.curr);
                }
            }
        });
    }
    /*end分页*/

    /*页面初始化*/
    function init() {
        renderPages(true);
        bindEvents();
        //日历
        $('#searchTimeRange').daterangepicker();
        $('#searchTimeRange').on('apply.daterangepicker',function(ev, picker) {
            $('#orderStartTime').val(picker.startDate.format('YYYY-MM-DD'));
            $('#orderEndTime').val(picker.endDate.format('YYYY-MM-DD'));
        });

        function getOptionValue(e){
            //console.log( $(e).text());
            var $me = $(e);
            $me.parents('.dropdown-menu').siblings('.dropdown-toggle').val($me.find('a').text());
            $me.parents('.dropdown-menu').siblings('.dropdown-toggle').attr("data",$me.attr("data"));
        }

        //下拉框
        $('.dropdown-menu li').on('click',function () {
            getOptionValue(this);
        });


    }
    
    /*end 页面初始化*/
    init();

    //日期转换
    function getFormatDate(date, pattern) {
        if (date == undefined) {
            date = new Date();
        }
        if (pattern == undefined) {
            pattern = "yyyy-MM-dd hh:mm:ss";
        }
        return date.format(pattern);
    }

    Date.prototype.format = function(format) {
        var o = {
            "M+" :this.getMonth() + 1, // month
            "d+" :this.getDate(), // day
            "h+" :this.getHours(), // hour
            "m+" :this.getMinutes(), // minute
            "s+" :this.getSeconds(), // second
            "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter
            "S" :this.getMilliseconds()
        };
        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        }
        for ( var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
};
