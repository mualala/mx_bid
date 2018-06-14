/**
 * Created by yanghm on 2018/5/15 0015.
 */
var product = {

    createProduct: {
        submit: function () {
            if (!$('#submitInfo').hasClass('disabled')) {
                var productInfo = {
                    productTypeId: $('#productType option:selected').val(),
                    code: $('#code').val(),
                    name: $('#name').val(),
                    spec: $('#spec').val(),
                    unit: $('#unit').val(),
                    maxUnitPrice: $('#maxUnitPrice').val(),
                    defaultGradient: $('#defaultGradient').val(),
                    productDesc: $('#productDesc').val()
                };

                $.ajax({
                    url: "/product",
                    type: "post",
                    contentType: "application/x-www-form-urlencoded",
                    // dataType: "json",
                    data: productInfo,
                    success: function (count) {
                        layer.msg('成功创建了 [ ' + count + ' ] 个产品', {icon: 1});
                        product.createProduct.resetInfo();
                    },
                    error: function (xhr, textStatus, errorThrown) {
                        var status = xhr.status;
                        layer.alert(
                            '创建产品失败,状态码 [ ' + status + ' ] ,原因:' + xhr.responseText,
                            {title: '提示框', icon: 0}
                        );
                    }
                });
            }
        },

        resetInfo: function () {
            $('#productType option:first').prop('selected', true);
            $('#code').val('');
            $('#name').val('');
            $('#spec').val('');
            $('#unit').val('');
            $('#maxUnitPrice').val('');
            $('#defaultGradient').val('');
            $('#productDesc').val('');

            $('#submitInfo').addClass('disabled');
        },

        checkInfo: function () {
            if(!$('#productType option:first').is(':selected') && $('#code').val() != '' && $('#name').val() != '' && $('#spec').val() != '' &&
                $('#unit').val() != '' && $('#maxUnitPrice').val() != '' && $('#defaultGradient').val() != '') {
                $('#submitInfo').removeClass('disabled');
            }else {
                $('#submitInfo').addClass('disabled');
            }
        }
    },

    productManage: {
        initProductReport: function () {
            $('#productReport').bootstrapTable('destroy');//先销毁表格

            //初始化表格，动态从服务器加载数据
            $('#productReport').bootstrapTable({
                method: 'post',
                url: 'productReport',
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
                toolbar: '#userBlockTool',
                singleSelect: false,//多选
                exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
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
                        productTypeId: $('#productType01 option:selected').val(),
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

        /* 删除产品 */
        deleteProduct: function () {
            var rows = $("#productReport").bootstrapTable('getAllSelections');
            if(rows.length == 0) {
                layer.alert(
                    '请选择要删除的产品',
                    {title: '提示框', icon: 0}
                );
            }else {
                //询问框
                layer.confirm('确定要删除选中的 [ ' + rows.length + ' ] 条产品吗 ?', {
                        btn: ['删除','再考虑一下~~'] //按钮
                    }, function(){
                        // layer.msg('的确很重要', {icon: 1});

                        var ids = '';
                        for(var index in rows) {
                            var id = rows[index].productId;
                            ids = id + '-' + ids;
                        }
                        $.ajax({
                            url: "/product/" + ids + "/product",
                            type: "delete",
                            contentType: "application/json",
                            dataType: "json",
                            success: function (count) {
                                layer.msg('成功删除了 [ ' + count + ' ] 个产品', {icon: 1});
                                product.productManage.initProductReport();
                            },
                            error: function (xhr, textStatus, errorThrown) {
                                var status = xhr.status;
                                layer.alert(
                                    '删除产品失败,状态码 [ ' + status + ' ]',
                                    {title: '提示框', icon: 0}
                                );
                            }
                        });
                    }
                    // function(){
                    // layer.msg('也可以这样', {
                    //     time: 20000, //20s后自动关闭
                    //     btn: ['明白了', '知道了']
                    // });
                    // }
                );
            }
        },

        // 修改产品信息
        modifyProduct: {
            toggleModal: function () {
                var rows = $("#productReport").bootstrapTable('getAllSelections');
                if(rows.length == 0) {
                    layer.alert(
                        '请选择要修改的产品!',
                        {title: '提示框', icon: 0}
                    );
                }else if (rows.length > 1) {
                    layer.alert(
                        '一次只能修改一个产品!',
                        {title: '提示框', icon: 0}
                    );
                }else {
                    $('#modifyProductModal').modal('toggle');
                    var rowValue = rows[0];
                    var id = rowValue.productId;

                    $('#productType02').empty();
                    product.productTypeNames($('#productType02'), rowValue.productType.productTypeId);

                    $('#productTypeId').val($('#productType02 option:selected').val());
                    $('#code').val(rowValue.code);
                    $('#name').val(rowValue.name);
                    $('#spec').val(rowValue.spec);
                    $('#unit').val(rowValue.unit);
                    $('#maxUnitPrice').val(rowValue.maxUnitPrice);
                    $('#defaultGradient').val(rowValue.defaultGradient);
                    $('#productDesc').val(rowValue.productDesc);
                }
            },

            submit: function () {
                var productInfo = {
                    productTypeId: $('#productType02 option:selected').val(),
                    code: $('#code').val(),
                    name: $('#name').val(),
                    spec: $('#spec').val(),
                    unit: $('#unit').val(),
                    maxUnitPrice: $('#maxUnitPrice').val(),
                    defaultGradient: $('#defaultGradient').val(),
                    productDesc: $('#productDesc').val(),
                };

                var rows = $("#productReport").bootstrapTable('getAllSelections');
                if(rows.length == 1) {
                    $.ajax({
                        url: "/product/" + rows[0].productId + "/one",
                        type: "put",
                        contentType: "application/x-www-form-urlencoded",
                        dataType: "json",
                        data: productInfo,
                        success: function (count) {
                            layer.msg('成功修改 [ ' + count + ' ] 个产品', {icon: 1});
                            product.productManage.initProductReport();
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            var status = xhr.status;
                            layer.alert(
                                '修改产品信息失败,状态码 [ ' + status + ' ]',
                                {
                                    title: '提示框',
                                    icon: 0
                                }
                            );
                        }
                    });
                }
            }
        },

        /*移除条件*/
        resetCondition: function () {
            $('#productType01 option:first').prop('selected', true);
            // $("#verifyState option[value='s1']").attr("selected", true);
            // $("#schoolName").val('');
            // $("#gender option:selected").val('');
            // $("#profession").val('');
            $("#startDate").val('');
            $("#endDate").val('');
            // $("#schoolID").val('');
            // $("#phoneNum").val('');
        }
    },

    productTypeNames: function (select_obj, selectId) {
        $.ajax({
            url: "/productType/productTypeNameList",
            type: "get",
            // contentType: "application/x-www-form-urlencoded",
            // dataType: "json",
            // data: productInfo,
            success: function (resp) {
                // select_obj.empty();
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
                    // select_obj.append('<option value=' + resp[index].productTypeId + '>' + resp[index].name + '</option>')
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
    }

}

