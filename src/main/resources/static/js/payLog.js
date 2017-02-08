/**
 * Created by Jackie.Chen on 2016/12/4.
 */

var DataList = function () {
    /*基础配置*/
    var el = 'body';
    var addLay = null,updateLay = null;
    var loadAreaSize = ['600px', '400px']; //宽高
    var searchObj = {
        payer:"",
        endTime:"",
        payType :"",
        pageNum: 1,
        pageSize: 10
    };

    /*end 基础配置*/

    /*操作*/
    function doSearch() {
        var startTime = $("#orderStartTime").val();
        var endTime= $("#orderEndTime").val();
        var payer =$("#payer").val();
        var payType =$("#serviceArea").attr("data");

        searchObj.startTime = startTime || '';
        searchObj.endTime = endTime || '';
        searchObj.payType = payType || '';
        searchObj.payer = payer || '';
        searchObj.pageNum = 1;  //reset
        renderPages(true);
    }


    /*end弹出框*/

    /*页面初始化和事件绑定*/
    function bindEvents() {
        /*update*/
        $(el).undelegate()
            .delegate('#findPayLogList','click',find_order);
        // .delegate('a[name=dataDelete]','click',doDelete)
        // .delegate('a[name=dataOffline]','click',doOffline)
        // .delegate('a[name=dataChangePrice]','click',doChangePrice)
        // .delegate('a[name=detail]','click',showDetails)
        // .delegate('#defineBtn','click',doSearch)
        // .delegate('#addTheme','click',renderAddPage);

    }
    function renderPages(isFirst,num) {
        getHtml(isFirst,num)
    }
    /*end页面初始化和事件绑定*/


    function getHtml(isFirst,num){
       var startTime= $("#orderStartTime").val()
        ajaxCommonFun({
            type:"GET",
            url: '/paylog/getPayLogList.do',
            dataType:'html',
            data: {
                "payer":$("#payer").val(),
                "payType":$("#serviceArea").attr("data"),
                "startTime":$("#orderStartTime").val(),
                "endTime":$("#orderEndTime").val(),
                "page":num,
                "pageSize":searchObj.pageSize
            },
            success: function (res) {
                $("#showPayLog").html(res);
                if(isFirst){
                    buildPageArea();
                }
            }
        });
    }

    /*<![CDATA[*/
    function find_order(){
        getHtml(true)
    }
    /*]]>*/


    /*分页*/
    function doChangePage(num) {

        renderPages(false,num);
    }
    function buildPageArea() {
        var pageAllCount = $('#pageAllCount').val(),
            totalPage = Math.ceil(pageAllCount/searchObj.pageSize);
        laypage({
            cont: 'pageAllCountLog',
            pages: totalPage,
            totalCount:pageAllCount,
            curr: 1,
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

        //$('#payTime').daterangepicker(  {
        //    singleDatePicker: true,
        //    startDate: moment()
        //});
        function getOptionValue(e){

            var $me = $(e);
            $me.parents('.dropdown-menu').siblings('.dropdown-toggle').val($me.find('a').text());
            $me.parents('.dropdown-menu').siblings('.dropdown-toggle').attr("data",$me.attr("data"));
        }

        //下拉框
        $('.dropdown-menu li').on('click',function () {
            getOptionValue(this);
        });
        //日历
        $('#searchTimeRange').daterangepicker();

        $('#searchTimeRange').on('apply.daterangepicker',function(ev, picker) {
            $('#orderStartTime').val(picker.startDate.format('YYYY-MM-DD'));
            $('#orderEndTime').val(picker.endDate.format('YYYY-MM-DD'));
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
