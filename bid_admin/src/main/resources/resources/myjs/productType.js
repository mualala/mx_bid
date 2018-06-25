/**
 * Created by yanghm on 2018/5/15 0015.
 */
var productType = {

    createProductType: {
        submit: function () {
            if (!$('#addProductType').hasClass('disabled')) {
                $.ajax({
                    url: "/productType",
                    type: "post",
                    contentType: "application/x-www-form-urlencoded",
                    // dataType: "json",
                    data: {name: $('#addName').val()},
                    success: function (count) {
                        layer.msg('成功创建了 [ ' + count + ' ] 个产品类型', {icon: 1});
                        $('#addName').val('');
                        productType.productTypeManage.initProductTypeReport();
                    },
                    error: function (xhr, textStatus, errorThrown) {
                        var status = xhr.status;
                        layer.alert(
                            '创建产品类型失败,状态码 [ ' + status + ' ] ,原因:' + xhr.responseText,
                            {title: '提示框', icon: 0}
                        );
                    }
                });
            }
        },

        checkInfo: function () {
            if($('#addName').val() != '') {
                $('#addProductType').removeClass('disabled');
            }else {
                $('#addProductType').addClass('disabled');
            }
        }
    },

    productTypeManage: {
        initProductTypeReport: function () {
            $('#productTypeReport').bootstrapTable('destroy');//先销毁表格

            //初始化表格，动态从服务器加载数据
            $('#productTypeReport').bootstrapTable({
                method: 'post',
                url: '/productType/productTypeReport',
                // height: "450",
                striped: false,//不显示斑马线
                clickToSelect: true,//点击行即可选中单选/复选框
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
                toolbar: '#productTypeBlockTool',
                singleSelect: false,//多选
                exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
                // uniqueId: "id", //每一行的唯一标识，一般为主键列
                pagination:true,
                // editable: false,
                sidePagination: 'server',//服务器端请求

                columns: [
                    {field: 'state', checkbox: true, width: 30},
                    {field: 'productTypeId', title: '产品类型id', sortable: true, visible: false},
                    {field: 'name', title: '产品类型名称', sortable: true},
                    {field: 'count', title: '产品数量'},
                    {field: 'createTime', title: '创建时间', sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
                            }
                        }
                    },
                    {field: 'updateTime', title: '最后修改时间', sortable: true,
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                return utils.dateFormat.timeStampToDate(value);
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
                        // searchText: $(".search").children('input').val(),
                        // verifyState: $("#verifyState option:selected").val(),
                        // blockState: $("#blockState option:selected").val(),
                        // school: $("#schoolName").val(),
                        // gender: $("#gender option:selected").val(),
                        // profession: $("#profession").val(),
                        startDate: $("#startDate").val(),
                        endDate: $("#endDate").val(),
                        sortName: params.sortName,
                        sortOrder: params.sortOrder,
                        // schoolID: $("#schoolID").val()
                    };
                    return param;
                },
                onLoadSuccess: function () {//加载成功时执行
                },
                onLoadError: function () {//加载失败时执行
                    layer.alert(
                        '加载数据失败 !',
                        {
                            title: '提示框',
                            icon: 0
                        }
                    );
                }
            });
        },

        /* 删除产品类型 */
        deleteProduct: function () {
            var rows = $("#productTypeReport").bootstrapTable('getAllSelections');
            if(rows.length == 0) {
                layer.alert(
                    '请选择要删除的产品类型',
                    {title: '提示框', icon: 0}
                );
            }else {
                //询问框
                layer.confirm('确定要删除选中的 [ ' + rows.length + ' ] 条产品类型吗 ?', {
                        btn: ['删除','不删除'] //按钮
                    }, function() {
                        var ids = '';
                        for(var index in rows) {
                            console.log(rows[index])
                            var id = rows[index].productTypeId;
                            ids = id + '-' + ids;
                        }
                        $.ajax({
                            url: "/productType/" + ids + "/productType",
                            type: "delete",
                            contentType: "application/json",
                            dataType: "json",
                            success: function (count) {
                                layer.msg('成功删除了 [ ' + count + ' ] 个产品类型', {icon: 1});
                                productType.productTypeManage.initProductTypeReport();
                            },
                            error: function (xhr, textStatus, errorThrown) {
                                var status = xhr.status;
                                layer.alert(
                                    '',
                                    {title: '提示框', icon: 0, content: '<h3>删除产品类型失败,状态码 [ '+status+']</h3>' + '<h5>原因: '+xhr.responseText+'</h5>'}
                                );
                            }
                        });
                    }
                );
            }
        },

        // 修改产品类型信息
        modifyProductType: {
            toggleModal: function () {
                var rows = $("#productTypeReport").bootstrapTable('getAllSelections');
                if(rows.length == 0) {
                    layer.alert(
                        '请选择要修改的产品类型!',
                        {title: '提示框', icon: 0}
                    );
                }else if (rows.length > 1) {
                    layer.alert(
                        '一次只能修改一个产品类别!',
                        {title: '提示框', icon: 0}
                    );
                }else {
                    $('#modifyProductTypeModal').modal('toggle');
                    var rowValue = rows[0];
                    $('#modifyName').val(rowValue.name);
                }
            },

            submit: function () {
                var rows = $("#productTypeReport").bootstrapTable('getAllSelections');
                if(rows.length == 1 && $('#modifyName').val() != rows[0].name && !$('#modifyProductType').hasClass('disabled')) {
                    $.ajax({
                        url: "/productType/" + rows[0].productTypeId + "/one",
                        type: "put",
                        contentType: "application/x-www-form-urlencoded",
                        dataType: "json",
                        data: {name: $('#modifyName').val()},
                        success: function (count) {
                            layer.msg('成功修改 [ ' + count + ' ] 个产品类型', {icon: 1});
                            productType.productTypeManage.initProductTypeReport();
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            var status = xhr.status;
                            layer.alert(
                                '修改产品类型信息失败,状态码 [ ' + status + ' ]',
                                {title: '提示框', icon: 0}
                            );
                        }
                    });
                }
            }
        },

        /*移除条件*/
        resetCondition: function () {
            // $("#verifyState option[value='s1']").attr("selected", true);
            // $("#schoolName").val('');
            // $("#gender option:selected").val('');
            // $("#profession").val('');
            $("#startDate").val('');
            $("#endDate").val('');
            // $("#schoolID").val('');
            // $("#phoneNum").val('');
        }
    }

}

