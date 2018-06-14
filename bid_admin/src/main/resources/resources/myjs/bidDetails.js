/**
 * Created by yanghm on 2018/6/6 0006.
 */

var bidDetails = {

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

    selectBidding: {
        initSelectBidReport: function () {
            $('#selectBidReport').bootstrapTable('destroy').bootstrapTable({
                method: 'post',
                url: '/bidding/biddingReport',
                // height: "450",
                striped: false,//不显示斑马线
                clickToSelect: false,//点击行即可选中单选/复选框
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                pagination: true,//启动分页
                pageSize: 15,//每页显示的记录数
                pageNumber: 1,//当前第几页
                pageList: [15, 50, 100, 500, 5000, 50000],//记录数可选列表
                search: true,//是否启用查询,是客户端client才有效
                searchOnEnterKey: true,//按回车触发搜索方法，否则自动触发搜索方法
                showColumns: true,//显示下拉框勾选要显示的列
                showExport: true,//是否显示导出
                exportDataType: 'basic',
                showRefresh: true,//显示刷新按钮
                silent: true,//刷新事件必须设置
                strictSearch: true,//全匹配搜索，否则为模糊搜索
                showToggle: true,//显示 切换试图（table/card）按钮
                toolbar: '#biddingBlockTool',
                singleSelect: false,//多选
                exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],  //导出文件类型
                // uniqueId: "id", //每一行的唯一标识，一般为主键列
                pagination:true,
                // editable: false,
                sidePagination: 'server',//服务器端请求

                columns: [
                    {field: 'state', checkbox: true, width: 30},
                    {field: 'id', title: 'id', visible: false},
                    {
                        field: 'type',
                        title: '竞标单类型',
                        sortable: true,
                        formatter: function (value, row, index) {
                            var typeMsg = ''
                            switch (value) {
                                case 0: typeMsg = '竞标单'; break;
                                case 1: typeMsg = '草稿'; break;
                                case 2: typeMsg = '垃圾箱'; break;
                            }
                            return typeMsg
                        }
                    },
                    {field: 'name', title: '竞标单名称', sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return '<a>' + value + '</a>'
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
                    {field: 'count', title: '类别数量', sortable: true},
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
                        field: 'endTime',
                        title: '竞标单截止日期',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
                    {
                        field: 'status',
                        title: '标单状态',
                        formatter: function (value, row, index) {
                            var statusMsg = ''
                            switch (value) {
                                case 0: statusMsg = '已发布'; break;
                                case 1: statusMsg = '正在竞标中'; break;
                                case 2: statusMsg = '已结束'; break;
                            }
                            return statusMsg
                        }
                    },
                    {field: 'bidDesc', title: '详细描述'},
                    {
                        field: 'createTime',
                        title: '竞标单创建日期',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
                    {
                        field: 'updateTime',
                        title: '竞标单更新日期',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
                ],

                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: 'undefined',

                queryParams: function queryParams(params) {
                    var param = {
                        name: $('#bidName').val(),
                        pageNumber: params.pageNumber,
                        pageSize: params.pageSize,
                        sortName: params.sortName,
                        sortOrder: params.sortOrder,
                        type: 0,
                        status: 2
                    }
                    return param
                },
                onLoadSuccess: function () {//加载成功时执行
                },
                onLoadError: function () {//加载失败时执行
                    layer.alert(
                        '加载数据失败 !',
                        {title: '提示框', icon: 0}
                    );
                },

                onClickCell: function (field, value, row, $element) {
                    // utils.storage.delSession('bidName') //先清除缓存
                    utils.storage.setSession('bidName', value)

                    if(field == 'name') {
                        var idx = layer.open({
                            type: 2,
                            area: ['70%', '50%'],
                            fixed: false, //不固定
                            shadeClose: true,
                            // resize: true,
                            // scrolling: auto,
                            // scrollbar: false,
                            maxmin: true,
                            content: 'screenBidding.html'
                            // style: 'position:fixed; left:0; top:0; width:70%; height:50%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;'
                        });
                        layer.iframeAuto(idx)
                    }
                },
            });
        },

        resetInfo: function () {
            $('#bidName').val('')
            $('#startDate').val('')
            $('#endDate').val('')
        }
    },


    initBidDetailsReport: function () {
        $('#bidDetailsReport').bootstrapTable('destroy').bootstrapTable({
            method: 'post',
            url: '/bidDetails/bidDetailsReport',
            // height: "450",
            striped: false,//不显示斑马线
            clickToSelect: true,//点击行即可选中单选/复选框
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded',
            pagination: true,//启动分页
            pageSize: 15,//每页显示的记录数
            pageNumber: 1,//当前第几页
            pageList: [15, 50, 100, 500, 5000, 50000],//记录数可选列表
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
                {field: 'bidDetailId', title: 'id', sortable: true, visible: false},
                {field: 'productId', title: 'productId', visible: false},
                {field: 'supplier.username', title: '供应商账号'},
                {field: 'supplier.name', title: '供应商名字'},
                {field: 'bidName', title: '竞标单名称', sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return '<a>' + value + '</a>'
                        }
                    }
                },
                {field: 'count', title: '出价次数', sortable: true},
                {field: 'sum', title: '总出价金额', sortable: true},
                {field: 'supplier.phone', title: '电话'},
                {field: 'supplier.companyName', title: '公司名称'},
                {field: 'bidding.bidDesc', title: '竞标单描述'},
                {
                    field: 'bidding.startTime',
                    title: '开标时间',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {
                    field: 'bidding.endTime',
                    title: '结束时间',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },


                {
                    field: 'createTime',
                    title: '供应商出价时间',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
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

                    username: $('#username').val(),
                    phone: $("#phone").val(),
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

            onClickCell: function (field, value, row, $element) {
                var priceInfo = {
                    uid: row.uid,
                    bidName: row.bidName,
                    productId: row.productId
                }
                utils.storage.setSession("priceInfo", JSON.stringify(priceInfo))
                // utils.storage.delSession('bidName') //先清除缓存
                utils.storage.setSession('bidName', value)

                if(field == 'bidName') {
                    layer.open({
                        type: 2,
                        area: ['1000px', '600px'],
                        fixed: false, //不固定
                        maxmin: true,
                        content: 'seePriceDetail.html'
                    });
                }
            },

        });
    },

    /* 出价详情 报表 */
    priceDetails: {
        initPriceDetails: function () {
            $('#priceDetailsReport').bootstrapTable('destroy').bootstrapTable({
                method: 'post',
                url: '/bidDetails/priceDetails',
                // height: "50%",
                striped: false,//不显示斑马线
                clickToSelect: true,//点击行即可选中单选/复选框
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                pagination: true,//启动分页
                pageSize: 10,//每页显示的记录数
                pageNumber: 1,//当前第几页
                pageList: [10, 20, 50, 100, 500, 5000, 50000, 'ALL'],//记录数可选列表
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
                // exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
                // uniqueId: "id", //每一行的唯一标识，一般为主键列
                pagination:true,
                // editable: false,
                sidePagination: 'server',//服务器端请求

                columns: [
                    // {field: 'state', checkbox: true, width: 30},
                    {
                        title: '序号',//标题  可不加
                        formatter: function (value, row, index) {
                            return index+1;
                        }
                    },
                    {field: 'bidDetailId', title: 'bidDetailId', visible: false},
                    {field: 'productId', title: 'pid', visible: false},

                    {field: 'bidName', title: '竞标单名称'},
                    {field: 'bidding.startPrice', title: '起拍价'},
                    {field: 'bidding.step', title: '阶梯价'},
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
                    {
                        field: 'bidding.startTime',
                        title: '起标时间',
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
                    {
                        field: 'bidding.endTime',
                        title: '结束时间',
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
                    {field: 'bidding.bidDesc', title: '竞标单描述'},

                    {field: 'productId', title: '产品ID'},
                    {field: 'product.code', title: '产品编码'},
                    {field: 'product.name', title: '产品名称'},
                    {field: 'bidding.number', title: '数量'},
                    {field: 'product.spec', title: '产品规格'},
                    {field: 'product.unit', title: '产品单位'},
                    {field: 'product.productDesc', title: '产品描述'},

                    {field: 'supplier.username', title: '投标人账号'},
                    {field: 'supplier.name', title: '投标人名称'},
                    {field: 'price', title: '出价金额', sortable: true,},
                    {field: 'supplier.phone', title: '投标人电话'},
                    {field: 'supplier.companyName', title: '公司名字'},
                    {field: 'supplier.legal', title: '法人'},
                    {
                        field: 'createTime',
                        title: '出价时间',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
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

                        bidName: $("#bidName").val(),
                        productId: $('#productId').val(),
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
            $('#bidName').val('')
            $('#productId').val('')
            $('#startDate').val('')
            $('#endDate').val('')
        }
    },


    resetInfo: function () {
        $('#name').val('')
        $('#phone').val('')
        $('#startDate').val('')
        $('#endDate').val('')
    }

}
