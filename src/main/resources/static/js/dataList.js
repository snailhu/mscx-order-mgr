/**
 * Created by Jackie.Chen on 2016/12/4.
 */

var DataList = function () {
    /*基础配置*/
    var el = 'body';
    var addLay = null,updateLay = null;
    var loadAreaSize = ['600px', '400px']; //宽高
    var searchObj = {
        startTime:"",
        endTime:"",
        userName :"",
        payType :"",
        orderStatus:"",
        pageNum: 1,
        pageSize: 10
    };
    
    /*end 基础配置*/




    function payLog(){
        //$('#payTime').val('');
        var obj = $(this);
        $('#payTime').val((new Date()).format("yyyy-MM-dd"));
        $('.input-notice-1').val("")
        $(".input-notice-"+3).attr("value", "0")

        for(var j =7;j>3;j--){
            if(j==5)continue
            $(".input-notice-"+j).attr("value", ($(obj).parent().parent().prevAll()[j]).innerText)
            if(j==4){
                $(".input-notice-"+4).attr("value", ($(obj).parent().parent().prevAll()[4]).innerText)
            }
        }
        layer.open({
            type: 1,
            btn: ['付款','关闭'],
            title: '付款登记',
            shade:  0.1, //遮罩透明度,
            shadeClose: true,
            area: ['760px', '450px'],
            content: $('#paySignDiv'), //捕获的元素
            btn1:function(index,layero){

                var  orderNum = $('.input-notice-7').val()
                var orderPay = $('.input-notice-4').val()
                var payer = $('.input-notice-6').val()
                var payCount = $('.input-notice-1').val()
                var payType = $('.payWay').attr("data")
                var payTime = $('#payTime').val()
                if(parseFloat(orderPay)==parseFloat(payCount)){
                    ajaxCommonFun({
                        type: 'POST',
                        url: '/order/payRecord.do',
                        data: {
                            "orderNum":orderNum,
                            "payer":payer,
                            "payCount":payCount,
                            "payType":payType,
                            "payTime":payTime
                        },
                        success: function (t) {
                            layer.close(index);
                            renderPages(true)
                        }
                    });
                }else{
                    // alert("请填写正确的支付金额")
                    $("#error_td").show();
                }
            },
            btn2:function(index,layero){
                $("#error_td").hide();
                layer.close(index);
            }
        });
    }

    /*操作*/
    function doSearch() {
        var startTime = $("#orderStartTime").val();
        var endTime= $("#orderEndTime").val();
        var userName =$("#userName").val();
        var payType =$("#payType").attr("data");
        var orderStatus = $("#orderStatus").attr("data");

        searchObj.startTime = startTime || '';
        searchObj.endTime = endTime || '';
        searchObj.payType = payType || '';
        searchObj.status = status || '';
        searchObj.orderStatus = orderStatus || '';
        searchObj.pageNum = 1;  //reset
        renderPages(true);
    }


    /*end弹出框*/

    /*页面初始化和事件绑定*/
    function bindEvents() {
        /*update*/
        $(el).undelegate()
            .delegate('#findOrderList','click',find_order)
            .delegate('.pay-sign','click',payLog)
           // .delegate('.orderNum','click',showDetail);
    }
    
    function renderPages(isFirst,num) {
        getHtml(isFirst,num)
    }
    /*end页面初始化和事件绑定*/

    $('.contNum').click(function(){
        layer.open({
            type: 1,
            btn: ['关闭'],
            title: '合同详情',
            shade:  0.1, //遮罩透明度,
            shadeClose: true,
            area: ['760px', '450px'],
            content: $('#contInfoDiv'), //捕获的元素
            btn1:function(index,layero){
                layer.close(index);
            }
        });
    });



    function getHtml(isFirst,num){
    	ajaxCommonFun({
            type:"GET",
            url: '/order/getOrderList.do',
            dataType:'html',
            data: {
                "userName":$("#userName").val(),
                "orderNum":$("#orderNum").val(),
                "payType":$("#payType").attr("data"),
                "orderStatus":$("#orderStatus").attr("data"),
                "startTime":$("#orderStartTime").val(),
                "endTime":$("#orderEndTime").val(),
                "page":num,
                "pageSize":searchObj.pageSize
            },
            success: function (res) {

                $("#showOrderList").html(res);
                if(isFirst){
                    buildPageArea();
                }


                $('#payTime').daterangepicker({
                    singleDatePicker: true,
                   startDate:  new Date().format("yyyy-MM-dd")
                });
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
        // searchObj.pageNum = num;
        renderPages(false,num);
    }
    
    function buildPageArea() {
        var pageAllCount = $('#pageAllCount').val(),
            totalPage = Math.ceil(pageAllCount/searchObj.pageSize);
        laypage({
            cont: 'pageAllCountOrder',
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
