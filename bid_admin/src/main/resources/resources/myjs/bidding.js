/**
 * Created by yanghm on 2018/5/22 0022.
 */
var bidding = {

    supplierDatas: [],//创建竞标单添加的供应商列表
    productDatas:[],//创建竞标单添加的产品列表

    selectedBiddings: [], //草稿要操作的竞标单数据
    // dustbinBiddins: [], //垃圾箱操作的竞标单数据

    checkBiddings: [], //选中要审核的竞标单

    products: {
        initProducts: function () {
            $('#getProducts').bootstrapTable('destroy').bootstrapTable({
                method: 'post',
                url: '/bidding/productReport',
                height: "350",
                striped: false,//不显示斑马线
                clickToSelect: true,//点击行即可选中单选/复选框
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                pagination: true,//启动分页
                pageSize: 5,//每页显示的记录数
                pageNumber: 1,//当前第几页
                pageList: [5, 10, 15],//记录数可选列表
                search: true,//是否启用查询,是客户端client才有效
                // searchOnEnterKey: true,//按回车触发搜索方法，否则自动触发搜索方法
                showColumns: true,//显示下拉框勾选要显示的列
                // showExport: true,//是否显示导出
                // exportDataType: 'basic',
                showRefresh: true,//显示刷新按钮
                silent: true,//刷新事件必须设置
                // strictSearch: true,//全匹配搜索，否则为模糊搜索
                showToggle: true,//显示 切换试图（table/card）按钮
                // toolbar: '#userBlockTool',
                singleSelect: false,//多选
                // exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx'],  //导出文件类型
                // uniqueId: "id", //每一行的唯一标识，一般为主键列
                pagination:true,
                // editable: false,
                sidePagination: 'server',//服务器端请求

                columns: [
                    {field: 'state', checkbox: true, width: 30},
                    {field: 'productType.name', title: '产品类型', sortable: true},
                    {field: 'productId', title: '产品id', sortable: true, visible: false},
                    {field: 'code', title: '产品编码'},
                    {field: 'name', title: '产品名称'},
                    {field: 'spec', title: '产品规格', width: 120},
                    {field: 'unit', title: '产品单位'},
                    {field: 'maxUnitPrice', title: '最高单价', sortable: true},
                    {field: 'defaultGradient', title: '缺省梯度'},
                    {field: 'productDesc', title: '产品描述'},
                    {
                        field: 'createTime',
                        title: '创建时间',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                                // return '<img src="' + value + '" class="img-rounded" alt="no pic" onclick="javascript:window.open(this.src);"></img>';
                            }
                        }
                    },
                    {
                        field: 'updateTime',
                        title: '最后修改时间',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                                // return '<img src="' + value + '" class="img-rounded" alt="no pic" onclick="javascript:window.open(this.src);"></img>';
                            }
                        }
                    }
                ],

                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: 'undefined',

                queryParams: function queryParams(params) {
                    //请求的分页参数
                    var param = {
                        pageNumber: params.pageNumber,
                        pageSize: params.pageSize,
                        productTypeId: $('#productType option:selected').val(),
                        // startDate: $("#startDate").val(),
                        // endDate: $("#endDate").val(),
                        sortName: params.sortName,
                        sortOrder: params.sortOrder,
                    };
                    return param;
                },
                onLoadSuccess: function () {//加载成功时执行
                },
                onLoadError: function () {//加载失败时执行
                    layer.alert(
                        '加载产品数据失败 !',
                        {title: '提示框', icon: 0}
                    );
                },

                //table选中行时触发事件
                onCheck: function (row, tr, field) {
                    row.number = 0
                    row.step = 0 //竞价阶梯
                    row.endDeliveryDate = ''
                    row.endPayDate = ''

                    var isDuplicat = false //非重复
                    for (var i in bidding.productDatas) {
                        var product = bidding.productDatas[i]
                        if(row.productId == product.productId) {
                            isDuplicat = true
                            layer.alert(
                                '已添加过该产品 !',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }
                    if (!isDuplicat) {
                        bidding.productDatas.push(row)
                    }
                },
                onUncheck: function (row, tr) {
                    for (var i in bidding.productDatas) {
                        var product = bidding.productDatas[i]
                        if(row.productId == product.productId) {
                            bidding.productDatas.splice(i, 1)
                        }
                    }
                },

                //table全部选中时
                onCheckAll: function (rows) {
                    //先清空所有元素
                    bidding.productDatas.splice(0, bidding.productDatas.length)

                    for(var i in rows) {
                        rows[i].number = 0
                        rows[i].step = 0 //竞价阶梯
                        rows[i].endDeliveryDate = ''
                        rows[i].endPayDate = ''
                        bidding.productDatas.push(rows[i])
                    }
                },
                onUncheckAll: function (rows) {
                    bidding.productDatas.splice(0, bidding.productDatas.length)
                }
            });
        }
    },

    //添加供应商 模块
    suppliers: {
        supplierTypeNames: function (select_obj, selectId) {
            $.ajax({
                url: "/bidding/supplierTypeNameList",
                type: "get",
                // contentType: "application/x-www-form-urlencoded",
                // dataType: "json",
                // data: productInfo,
                success: function (resp) {
                    for (var index in resp) {
                        var option;
                        var supplierTypeId = resp[index].supplierTypeId;
                        var name = resp[index].name;
                        if(selectId != null && selectId == supplierTypeId) {
                            option = $("<option selected>").val(supplierTypeId).text(resp[index].name);
                        }else {
                            option = $("<option>").val(supplierTypeId).text(resp[index].name);
                        }
                        select_obj.append(option);
                    }
                },
                error: function (xhr, textStatus, errorThrown) {
                    var status = xhr.status;
                    layer.alert(
                        '查询供应商类型失败,状态码 [ ' + status + ' ] ,原因:' + xhr.responseText,
                        {title: '提示框', icon: 0}
                    );
                }
            });
        }, 
        
        initSuppliers: function () {
            $('#getSuppliers').bootstrapTable('destroy').bootstrapTable({
                method: 'post',
                url: '/bidding/supplierReport',
                height: "350",
                striped: false,//不显示斑马线
                clickToSelect: true,//点击行即可选中单选/复选框
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                pagination: true,//启动分页
                pageSize: 5,//每页显示的记录数
                pageNumber: 1,//当前第几页
                pageList: [5, 10, 15],//记录数可选列表
                search: true,//是否启用查询,是客户端client才有效
                searchOnEnterKey: true,//按回车触发搜索方法，否则自动触发搜索方法
                showColumns: true,//显示下拉框勾选要显示的列
                showExport: true,//是否显示导出
                exportDataType: 'basic',
                showRefresh: true,//显示刷新按钮
                silent: true,//刷新事件必须设置
                strictSearch: true,//全匹配搜索，否则为模糊搜索
                showToggle: true,//显示 切换试图（table/card）按钮
                toolbar: '#supplierBlockTool',
                singleSelect: false,//多选
                exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
                // uniqueId: "id", //每一行的唯一标识，一般为主键列
                pagination:true,
                // editable: false,
                sidePagination: 'server',//服务器端请求

                columns: [
                    {field: 'state', checkbox: true, width: 30},
                    {field: 'supplierType.name', title: '供应商类型', sortable: true},
                    {field: 'supplierId', title: '供应商ID', sortable: true, visible: false},
                    {field: 'username', title: '供应商账号'},
                    {field: 'companyName', title: '公司名称'},
                    {field: 'licenseNumber', title: '营业执照号码'},
                    {field: 'legal', title: '法定代表人'},
                    {field: 'name', title: '联系人'},
                    {field: 'phone', title: '电话'},
                    {field: '邮箱', title: 'email'},
                    {field: 'fax', title: '传真'},
                    {field: 'companyAddress', title: '公司地址'},
                    {field: 'zipCode', title: '邮编'},
                    {field: 'companyWeb', title: '公司网址'},
                    {field: 'companyLevel', title: '公司资质等级'},
                    {field: 'companyDesc', title: '公司简介'},
                    {
                        field: 'createTime',
                        title: '创建时间',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                                // return '<img src="' + value + '" class="img-rounded" alt="no pic" onclick="javascript:window.open(this.src);"></img>';
                            }
                        }
                    },
                    {
                        field: 'updateTime',
                        title: '最后修改时间',
                        sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                                // return '<img src="' + value + '" class="img-rounded" alt="no pic" onclick="javascript:window.open(this.src);"></img>';
                            }
                        }
                    }
                ],

                //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
                //设置为limit可以获取limit, offset, search, sort, order
                queryParamsType: 'undefined',

                queryParams: function queryParams(params) {
                    //请求的分页参数
                    var param = {
                        pageNumber: params.pageNumber,
                        pageSize: params.pageSize,
                        supplierTypeId: $('#supplierType option:selected').val(),

                        // startDate: $("#startDate").val(),
                        // endDate: $("#endDate").val(),
                        sortName: params.sortName,
                        sortOrder: params.sortOrder,
                    };
                    return param;
                },
                onLoadSuccess: function () {//加载成功时执行
                },
                onLoadError: function () {//加载失败时执行
                    layer.alert(
                        '加载数据失败 !',
                        {title: '提示框', icon: 0}
                    );
                },

                /* table选中行时触发事件 */
                onCheck: function (row, tr, field) {
                    var isDuplicat = false //非重复
                    for (var i in bidding.supplierDatas) {
                        var su = bidding.supplierDatas[i]
                        if(row.supplierId == su.supplierId) {
                            isDuplicat = true
                            layer.alert(
                                '已添加过该供应商 !',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }
                    if (!isDuplicat) {
                        bidding.supplierDatas.push(row)
                    }
                },
                onUncheck: function (row, tr) {
                    for (var i in bidding.supplierDatas) {
                        var su = bidding.supplierDatas[i]
                        if(row.supplierId == su.supplierId) {
                            bidding.supplierDatas.splice(i, 1)
                        }
                    }
                },
                /* table全部选中时 */
                onCheckAll: function (rows) {
                    //先清空所有元素
                    bidding.supplierDatas.slice(0, bidding.supplierDatas.length)

                    for(var i in rows) {
                        bidding.supplierDatas.push(rows[i])
                    }
                },
                onUncheckAll: function (rows) {
                    bidding.supplierDatas.splice(0, bidding.supplierDatas.length)
                }
            });
        },
    },

    biddingManage: {
        initBiddingReport: function (type, status) {
            $('#biddingReport').bootstrapTable('destroy').bootstrapTable({
                method: 'post',
                url: '/bidding/biddingReport',
                // height: "450",
                striped: false,//不显示斑马线
                clickToSelect: false,//点击行即可选中单选/复选框
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                pagination: true,//启动分页
                pageSize: 10,//每页显示的记录数
                pageNumber: 1,//当前第几页
                pageList: [10, 20, 50, 100, 500, 5000, 50000],//记录数可选列表
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
                    {field: 'count', title: '产品数目', sortable: true},
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
                                case 3: statusMsg = '待审核'; break;
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
                    //请求的分页参数
                    if (ho != undefined) {
                        ho.queryCondition.pageNumber = params.pageNumber
                        ho.queryCondition.pageSize = params.pageSize
                        ho.queryCondition.sortName = params.sortName
                        ho.queryCondition.sortOrder = params.sortOrder
                        if(type == 0 || type == 1 || type == 2) ho.queryCondition.type = type
                        if (status == 0 || status == 1 || status == 2 || status == 3) ho.queryCondition.status = status
                        return ho.queryCondition
                    }else {
                        var param = {
                            pageNumber: params.pageNumber,
                            pageSize: params.pageSize,
                            sortName: params.sortName,
                            sortOrder: params.sortOrder,
                        }
                        if(type == 0 || type == 1 || type == 2) param.type = type
                        if (status == 0 || status == 1 || status == 2 || status == 3) param.status = status
                        return param
                    }
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
                        var content = type == 0 ? 'seeProductDetail.html' : 'modifyProductDetail.html'
                        //iframe层-父子操作
                        layer.open({
                            type: 2,
                            area: ['100%', '100%'],
                            fixed: false, //不固定
                            maxmin: true,
                            content: content
                        });
                    }
                },

                onCheck: function (row, tr, field) {
                    bidding.selectedBiddings.push(row.name)

                    bidding.checkBiddings.push(row.name)
                },
                onUncheck: function (row, tr) {
                    for (var i in bidding.selectedBiddings) {
                        var bidName = bidding.selectedBiddings[i]
                        if(row.name == bidName) {
                            bidding.selectedBiddings.splice(i, 1)
                        }
                    }

                    for (var i in bidding.checkBiddings) {
                        var bidName = bidding.checkBiddings[i]
                        if(row.name == bidName) {
                            bidding.checkBiddings.splice(i, 1)
                        }
                    }
                },

                //table全部选中时
                onCheckAll: function (rows) {
                    //先清空所有元素
                    bidding.selectedBiddings.splice(0, bidding.selectedBiddings.length)
                    bidding.checkBiddings.splice(0, bidding.checkBiddings.length)

                    for(var i in rows) {
                        bidding.selectedBiddings.push(rows[i].name)
                        bidding.checkBiddings.push(rows[i].name)
                    }
                },
                onUncheckAll: function (rows) {
                    bidding.selectedBiddings.splice(0, bidding.selectedBiddings.length)
                    bidding.checkBiddings.splice(0, bidding.checkBiddings.length)
                }
            });
        },

        initCheckBiddingReport: function (type, status) {
            $('#biddingReport').bootstrapTable('destroy').bootstrapTable({
                method: 'post',
                url: '/checkBidding/checkBiddingReport',
                // height: "450",
                striped: false,//不显示斑马线
                clickToSelect: false,//点击行即可选中单选/复选框
                dataType: 'json',
                contentType: 'application/x-www-form-urlencoded',
                pagination: true,//启动分页
                pageSize: 10,//每页显示的记录数
                pageNumber: 1,//当前第几页
                pageList: [10, 20, 50, 100, 500, 5000, 50000],//记录数可选列表
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
                    {field: 'count', title: '产品数目', sortable: true},
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
                                case 3: statusMsg = '待审核'; break;
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
                    //请求的分页参数
                    if (ho != undefined) {
                        ho.queryCondition.pageNumber = params.pageNumber
                        ho.queryCondition.pageSize = params.pageSize
                        ho.queryCondition.sortName = params.sortName
                        ho.queryCondition.sortOrder = params.sortOrder
                        if(type == 0 || type == 1 || type == 2) ho.queryCondition.type = type
                        if (status == 0 || status == 1 || status == 2 || status == 3) ho.queryCondition.status = status
                        return ho.queryCondition
                    }else {
                        var param = {
                            pageNumber: params.pageNumber,
                            pageSize: params.pageSize,
                            sortName: params.sortName,
                            sortOrder: params.sortOrder,
                        }
                        if(type == 0 || type == 1 || type == 2) param.type = type
                        if (status == 0 || status == 1 || status == 2 || status == 3) param.status = status
                        return param
                    }
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
                        var content = type == 0 ? '/bidding/seeProductDetail.html' : '/bidding/modifyProductDetail.html'
                        //iframe层-父子操作
                        layer.open({
                            type: 2,
                            area: ['100%', '100%'],
                            fixed: false, //不固定
                            maxmin: true,
                            content: content
                        });
                    }
                },

                onCheck: function (row, tr, field) {
                    bidding.selectedBiddings.push(row.name)

                    bidding.checkBiddings.push(row.name)
                },
                onUncheck: function (row, tr) {
                    for (var i in bidding.selectedBiddings) {
                        var bidName = bidding.selectedBiddings[i]
                        if(row.name == bidName) {
                            bidding.selectedBiddings.splice(i, 1)
                        }
                    }

                    for (var i in bidding.checkBiddings) {
                        var bidName = bidding.checkBiddings[i]
                        if(row.name == bidName) {
                            bidding.checkBiddings.splice(i, 1)
                        }
                    }
                },

                //table全部选中时
                onCheckAll: function (rows) {
                    //先清空所有元素
                    bidding.selectedBiddings.splice(0, bidding.selectedBiddings.length)
                    bidding.checkBiddings.splice(0, bidding.checkBiddings.length)

                    for(var i in rows) {
                        bidding.selectedBiddings.push(rows[i].name)
                        bidding.checkBiddings.push(rows[i].name)
                    }
                },
                onUncheckAll: function (rows) {
                    bidding.selectedBiddings.splice(0, bidding.selectedBiddings.length)
                    bidding.checkBiddings.splice(0, bidding.checkBiddings.length)
                }
            });
        },

        //删除竞标单
        deleteBid: function () {
            var len = bidding.selectedBiddings.length
            if(len > 0) {
                layer.confirm('确定删除选中的 [ ' + len + ' ] 个竞标单吗 ?', {
                        btn: ['删除','再考虑一下~~'] //按钮
                    }, function() {
                        var names = '';
                        for(var index in bidding.selectedBiddings) {
                            names = bidding.selectedBiddings[index] + '-' + names;
                        }
                        $.ajax({
                            url: "/bidding/" + names + "/bidding",
                            type: "delete",
                            contentType: "application/json",
                            dataType: "json",
                            success: function (count) {
                                layer.msg('成功删除了 [ ' + len + ' ] 个竞标单, 其中包含 [' + count + '] 个产品', {icon: 1});
                                bidding.selectedBiddings.splice(0, bidding.selectedBiddings.length)
                                bidding.biddingManage.initBiddingReport();
                            },
                            error: function (xhr, textStatus, errorThrown) {
                                var status = xhr.status;
                                layer.alert(
                                    '',
                                    {title: '提示框', icon: 0,
                                        content: '<h3>删除草稿失败,状态码 [ '+ status +'] </h3><h5>原因: ' + xhr.responseJSON.msg + '</h5>'
                                    }
                                );
                            }
                        });
                    }
                );
            }else {
                layer.alert(
                    '请选择要删除的竞标单 !',
                    {title: '提示框', icon: 0}
                );
            }
        },
    },

    productTypeNames: function (select_obj, selectId) {
        $.ajax({
            url: "/bidding/productTypeNameList",
            type: "get",
            success: function (resp) {
                for (var index in resp) {
                    var option;
                    var productTypeId = resp[index].productTypeId;
                    var name = resp[index].name;
                    if(selectId != null && selectId == productTypeId) {
                        option = $("<option selected>").val(resp[index].productTypeId).text(resp[index].name);
                    }else {
                        option = $("<option>").val(resp[index].productTypeId).text(resp[index].name);
                    }
                    select_obj.append(option);
                }
            },
            error: function (xhr, textStatus, errorThrown) {
                var status = xhr.status;
                layer.alert(
                    '查询产品类型失败,状态码 [ ' + status + ' ] ,原因:' + xhr.responseText,
                    {title: '提示框', icon: 0}
                );
            }
        });
    },

    deleteAll: function () {
        layer.confirm('确定删除全部竞标产品吗 ?', {
                btn: ['全部删除','再考虑一下~~'] //按钮
            }, function(){
                layer.msg('删除成功 !', {icon: 1});
                bidding.productDatas.splice(0, bidding.productDatas.length)
            }
        );
    }


}
