/**
 * Created by yanghm on 2018/6/6 0006.
 */

var doBidDetails = {

    initLayDate: function (obj_str) {
        laydate({
            elem: obj_str, //需显示日期的元素选择器 '#startDate'
            event: 'click', //触发事件
            format: 'YYYY-MM-DD hh:mm:ss', //日期格式
            istime: true, //是否开启时间选择
            isclear: true, //是否显示清空
            istoday: true, //是否显示今天
            issure: true, //是否显示确认
            festival: true, //是否显示节日
            min: '1900-01-01 00:00:00', //最小日期
            max: '2299-12-31 23:59:59', //最大日期
//        start: '2014-6-15 23:00:00',    //开始日期
            fixed: false, //是否固定在可视区域
            zIndex: 99999999, //css z-index
            choose: function(dates) { //选择好日期的回调
                console.log(dates)
                $(obj_str).val(dates)
            }
        })
    },

    initDoBidDetailsReport: function () {
        $('#doBidDetailsReport').bootstrapTable('destroy').bootstrapTable({
            method: 'post',
            url: '/winBid/doBidDetailsReport',
            // height: "450",
            striped: false,//不显示斑马线
            clickToSelect: true,//点击行即可选中单选/复选框
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded',
            pagination: true,//启动分页
            pageSize: 10,//每页显示的记录数
            pageNumber: 1,//当前第几页
            pageList: [10, 20, 50, 100, 500, 1000, 5000, 10000, 'ALL'],//记录数可选列表
            search: true,//是否启用查询,是客户端client才有效
            searchOnEnterKey: true,//按回车触发搜索方法，否则自动触发搜索方法
            showColumns: true,//显示下拉框勾选要显示的列
            showExport: true,//是否显示导出
            exportDataType: 'basic',
            showRefresh: true,//显示刷新按钮
            silent: true,//刷新事件必须设置
            strictSearch: true,//全匹配搜索，否则为模糊搜索
            showToggle: true,//显示 切换试图（table/card）按钮
            // toolbar: '#supplierBlockTool',
            singleSelect: false,//多选
            exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
            // uniqueId: "id", //每一行的唯一标识，一般为主键列
            pagination:true,
            // editable: false,
            sidePagination: 'server',//服务器端请求

            columns: [
                // {field: 'state', checkbox: true, width: 30},
                {field: 'winBidId', title: 'winBidId', visible: false},
                {field: 'productId', title: 'pid', visible: false},
                {field: 'bidName', title: '竞标单名称', sortable: true},
                {
                    field: 'bidding.mark',
                    title: '标记',
                    // sortable: true,
                    formatter: function (value, row, index) {
                        var markMsg = ''
                        switch (value) {
                            case 0: markMsg = '异常标记..'; break;
                            case 1: markMsg = '招标'; break;
                            case 2: markMsg = '竞标'; break;
                        }
                        return markMsg
                    }
                },

                {field: 'product.name', title: '产品名称'},
                {field: 'bidding.number', title: '数量'},
                {field: 'supplier.username', title: '中标人账号', visible: false},
                {field: 'supplier.name', title: '中标人名称'},
                {field: 'bidDetails.optimal', title: '最优出价', sortable: true,
                    cellStyle: function (value, row, index, field) {
                        return {classes: 'warning'};
                    }
                },
                {field: 'bidDetails.price', title: '中标金额', sortable: true,
                    cellStyle: function (value, row, index, field) {
                        return {classes: 'success'};
                    }
                },
                {field: 'reason', title: '中标理由',
                    cellStyle: function (value, row, index, field) {
                        return {classes: 'info'};
                    }
                },
                {
                    field: 'bidDetails.createTime',
                    title: '出价时间',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {field: 'supplier.phone', title: '中标人电话'},
                {field: 'supplier.companyName', title: '公司名字'},
                {field: 'supplier.legal', title: '法人', visible: false},

                {field: 'product.code', title: '产品编码', visible: false},
                {field: 'product.spec', title: '产品规格'},
                {field: 'product.unit', title: '产品单位'},
                {field: 'product.productDesc', title: '产品描述', visible: false},

                {field: 'bidding.startPrice', title: '起拍价'},
                {field: 'bidding.step', title: '阶梯价'},
                {
                    field: 'bidding.endDeliveryDate',
                    title: '交货期限',
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {
                    field: 'bidding.endPayDate',
                    title: '付款期限',
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {field: 'bidding.bidDesc', title: '标单描述', visible: false},
            ],

            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: 'undefined',

            queryParams: function queryParams(params) {
                //请求的分页参数
                var param = {
                    pageNumber: params.pageNumber,
                    pageSize: params.pageSize,
                    sortName: params.sortName,
                    sortOrder: params.sortOrder,

                    username: $('#sName').val(),
                    bidName: $("#bidName").val(),
                    startDate: $("#startDate").val(),
                    endDate: $("#endDate").val(),
                };
                return param;
            },
            onLoadSuccess: function () {//加载成功时执行
            },
            onLoadError: function () {//加载失败时执行
                layer.alert(
                    '加载数据失败 !',
                    {title: '提示框', icon: 0}
                )
            },
        });
    },

    resetInfo: function () {
        $('#sName').val('')
        $('#bidName').val('')
        $('#startDate').val('')
        $('#endDate').val('')
    }

}
