/**
 * Created by yanghm on 2018/5/31 0031.
 */

var report = {

    // status=0:发布状态(公告状态) 1:正在竞标中 2:结束
    initBidding: function (status, mark) {
        var obj = ''
        if (status == 0) {
            obj = $('#biddingReport00')
        }else if (status == 1) {
            obj = $('#biddingReport01')
        }else if (status == 2) {
            obj = $('#biddingReport02')
        }

        if(mark == 0) {
            obj = $('#biddingReportNotice')
        }else if(mark == 1) {
            obj = $('#biddingReport')
        }else if(mark == 2) {
            obj = $('#myBiddingReport')
        }

        obj.bootstrapTable('destroy').bootstrapTable({
            method: 'post',
            url: '/bidding/biddingReport',
            // height: "450",
            striped: false,//不显示斑马线
            clickToSelect: false,//点击行即可选中单选/复选框
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded',
            pagination: true,//启动分页
            pageSize: 5,//每页显示的记录数
            pageNumber: 1,//当前第几页
            pageList: [5, 10],//记录数可选列表
            search: false,//是否启用查询,是客户端client才有效
            searchOnEnterKey: false,//按回车触发搜索方法，否则自动触发搜索方法
            showColumns: false,//显示下拉框勾选要显示的列
            showExport: false,//是否显示导出
            exportDataType: 'basic',
            showRefresh: false,//显示刷新按钮
            silent: false,//刷新事件必须设置
            strictSearch: true,//全匹配搜索，否则为模糊搜索
            showToggle: false,//显示 切换试图（table/card）按钮
            toolbar: '#biddingBlockTool',
            singleSelect: false,//多选
            exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],  //导出文件类型
            // uniqueId: "id", //每一行的唯一标识，一般为主键列
            pagination:true,
            // editable: false,
            sidePagination: 'server',//服务器端请求

            columns: [
                // {field: 'state', checkbox: true, width: 30},
                {field: 'id', title: 'id', visible: false},
                {field: 'name', title: '竞标单名称', sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return '<a>' + value + '</a>'
                        }
                    }
                },
                {field: 'count', title: '包含的产品(个)', sortable: true},
                {
                    field: 'startTime',
                    title: '启标日期',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {
                    field: 'mark',
                    title: '标记',
                    sortable: true,
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
                {
                    field: 'endTime',
                    title: '竞标单截止日期',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {field: 'bidDesc', title: '详细描述'},
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
                    status: status,
                    type: 0,
                }
                return param
            },
            onLoadSuccess: function () {//加载成功时执行
            },
            onLoadError: function () {//加载失败时执行
            },

            onClickCell: function (field, value, row, $element) {
                // utils.storage.delSession('bidName') //先清除缓存
                utils.storage.setSession('bidName', value)

                if(field == 'name') {
                    layer.open({
                        type: 2,
                        area: ['100%', '100%'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: 'seeProductDetail.html'
                    });
                }
            },
        });
    },


    initMyBidding: function () {
        $('#myBiddingReport').bootstrapTable('destroy').bootstrapTable({
            method: 'post',
            url: '/bidding/myBiddingReport',
            // height: "450",
            striped: false,//不显示斑马线
            clickToSelect: false,//点击行即可选中单选/复选框
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded',
            pagination: true,//启动分页
            pageSize: 5,//每页显示的记录数
            pageNumber: 1,//当前第几页
            pageList: [5, 10],//记录数可选列表
            search: false,//是否启用查询,是客户端client才有效
            searchOnEnterKey: false,//按回车触发搜索方法，否则自动触发搜索方法
            showColumns: false,//显示下拉框勾选要显示的列
            showExport: false,//是否显示导出
            exportDataType: 'basic',
            showRefresh: false,//显示刷新按钮
            silent: false,//刷新事件必须设置
            strictSearch: true,//全匹配搜索，否则为模糊搜索
            showToggle: false,//显示 切换试图（table/card）按钮
            toolbar: '#biddingBlockTool',
            singleSelect: false,//多选
            exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],  //导出文件类型
            // uniqueId: "id", //每一行的唯一标识，一般为主键列
            pagination:true,
            // editable: false,
            sidePagination: 'server',//服务器端请求

            columns: [
                // {field: 'state', checkbox: true, width: 30},
                {field: 'id', title: 'id', visible: false},
                {field: 'name', title: '竞标单名称', sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return '<a>' + value + '</a>'
                        }
                    }
                },
                {field: 'count', title: '包含的产品(个)', sortable: true},
                {
                    field: 'startTime',
                    title: '启标日期',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {
                    field: 'mark',
                    title: '标记',
                    sortable: true,
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
                {
                    field: 'status',
                    title: '状态',
                    sortable: true,
                    formatter: function (value, row, index) {
                        var markMsg = ''
                        switch (value) {
                            case 0: markMsg = '发布状态'; break;
                            case 1: markMsg = '正在竞标'; break;
                            case 2: markMsg = '结束'; break;
                            case 3: markMsg = '审核'; break;
                        }
                        return markMsg
                    }
                },
                {
                    field: 'endTime',
                    title: '竞标单截止日期',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {field: 'bidDesc', title: '详细描述'},
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
                }
                return param
            },
            onLoadSuccess: function () {//加载成功时执行
            },
            onLoadError: function () {//加载失败时执行
            },

            onClickCell: function (field, value, row, $element) {
                // utils.storage.delSession('bidName') //先清除缓存
                utils.storage.setSession('bidName', value)
                utils.storage.setSession('mark', row.mark)

                if(field == 'name') {
                    layer.open({
                        type: 2,
                        area: ['100%', '100%'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: 'myProductDetail.html'
                    });
                }
            },
        });
    },

}