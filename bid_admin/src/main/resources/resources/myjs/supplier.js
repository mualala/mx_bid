/**
 * Created by yanghm on 2018/5/18 0015.
 */
var supplier = {

    createSupplier: {
        submit: function () {
            if (!$('#submitInfo').hasClass('disabled') && $('#errorTip').hasClass('fa-check')) {
                var supplierInfo = {
                    username: $('#username').val(),
                    password: $('#password').val(),
                    supplierTypeId: $('#supplierType option:selected').val(),
                    companyName: $('#companyName').val(),
                    licenseNumber: $('#licenseNumber').val(),
                    legal: $('#legal').val(),
                    name: $('#name').val(),
                    email: $('#email').val(),
                    phone: $('#phone').val(),
                    fax: $('#fax').val(),
                    zipCode: $('#zipCode').val(),
                    companyAddress: $('#companyAddress').val(),
                    companyWeb: $('#companyWeb').val(),
                    companyLevel: $('#companyLevel').val(),
                    companyDesc: $('#companyDesc').val()
                };

                $.ajax({
                    url: "/supplier",
                    type: "post",
                    contentType: "application/x-www-form-urlencoded",
                    // dataType: "json",
                    data: supplierInfo,
                    success: function (count) {
                        layer.msg('成功创建了 [ ' + count + ' ] 个供应商', {icon: 1});
                        supplier.createSupplier.resetInfo();
                    },
                    error: function (xhr, textStatus, errorThrown) {
                        var status = xhr.status;
                        layer.alert(
                            '创建供应商失败,状态码 [ ' + status + ' ] ,原因:' + xhr.responseText,
                            {title: '提示框', icon: 0}
                        );
                    }
                });
            }
        },

        resetInfo: function () {
            $('#username').val('');
            $('#password').val('');
            $('#supplierType').val('');
            $('#supplierType option:first').prop('selected', true);
            $('#companyName').val('');
            $('#licenseNumber').val('');
            $('#legal').val('');
            $('#name').val('');
            $('#email').val('');
            $('#phone').val('');
            $('#fax').val('');
            $('#zipCode').val('');
            $('#companyAddress').val('');
            $('#companyWeb').val('');
            $('#companyLevel').val('');
            $('#companyDesc').val('');

            $('#submitInfo').addClass('disabled');
            $('#errorTip').attr('class', 'hidden');
        },

        checkInfo: function () {
            if(!$('#supplierType option:first').is(':selected') && $('#username').val() != '' && $('#password').val() != '' && $('#supplierType').val() != '' && $('#companyName').val() != '' &&
                $('#licenseNumber').val() != '' && $('#legal').val() != '' && $('#name').val() != '' && $('#phone').val() != '' &&
                $('#errorTip').hasClass('fa-check')) {
                $('#submitInfo').removeClass('disabled');
            }else {
                $('#submitInfo').addClass('disabled');
            }
        }
    },

    supplierManage: {
        initSupplierReport: function () {
            $('#supplierReport').bootstrapTable('destroy');//先销毁表格

            //初始化表格，动态从服务器加载数据
            $('#supplierReport').bootstrapTable({
                method: 'post',
                url: 'supplierReport',
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
                    {field: 'supplierId', title: '供应商id', sortable: true, visible: false},
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
                        supplierTypeId: $('#supplierType01 option:selected').val(),
                        // searchText: $(".search").children('input').val(),
                        // verifyState: $("#verifyState option:selected").val(),
                        // blockState: $("#blockState option:selected").val(),
                        // school: $("#schoolName").val(),
                        // gender: $("#gender option:selected").val(),
                        companyName: '%' + $("#companyName").val() + '%',
                        phone: $("#phone").val(),

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
                        {title: '提示框', icon: 0}
                    );
                }
            });
        },

        /* 删除产品 */
        deleteSupplier: function () {
            var rows = $("#supplierReport").bootstrapTable('getAllSelections');
            if(rows.length == 0) {
                layer.alert(
                    '请选择要删除的供应商',
                    {title: '提示框', icon: 0}
                );
            }else {
                //询问框
                layer.confirm('确定要删除选中的 [ ' + rows.length + ' ] 个供应商吗 ?', {
                        btn: ['删除','不删除'] //按钮
                    }, function(){
                        var ids = '';
                        for(var index in rows) {
                            console.log(rows[index])
                            var id = rows[index].supplierId;
                            ids = id + '-' + ids;
                        }
                        $.ajax({
                            url: "/supplier/" + ids + "/supplier",
                            type: "delete",
                            contentType: "application/json",
                            dataType: "json",
                            success: function (count) {
                                layer.msg('成功删除了 [ ' + count + ' ] 个供应商', {icon: 1});
                                supplier.supplierManage.initSupplierReport();
                            },
                            error: function (xhr, textStatus, errorThrown) {
                                var status = xhr.status;
                                layer.alert(
                                    '删除供应商失败,状态码 [ ' + status + ' ]',
                                    {title: '提示框', icon: 0}
                                );
                            }
                        });
                    }
                );
            }
        },

        // 修改供应商信息
        modifySupplier: {
            toggleModal: function () {
                var rows = $("#supplierReport").bootstrapTable('getAllSelections');
                if(rows.length == 0) {
                    layer.alert(
                        '请选择要修改的供应商 !',
                        {title: '提示框', icon: 0}
                    );
                }else if (rows.length > 1) {
                    layer.alert(
                        '一次只能修改一个供应商 !',
                        {title: '提示框', icon: 0}
                    );
                }else {
                    $('#modifySupplierModal').modal('toggle');
                    var rowValue = rows[0];
                    var id = rowValue.supplierId;

                    $('#supplierType02').empty();
                    supplier.supplierTypeNames($('#supplierType02'), rowValue.supplierType.supplierTypeId);

                    $('#companyName02').val(rowValue.companyName);
                    $('#licenseNumber').val(rowValue.licenseNumber);
                    $('#legal').val(rowValue.legal);
                    $('#phone02').val(rowValue.phone);
                    $('#name').val(rowValue.name);
                    $('#email').val(rowValue.email);
                    $('#fax').val(rowValue.fax);
                    $('#zipCode').val(rowValue.zipCode);
                    $('#companyAddress').val(rowValue.companyAddress);
                    $('#companyWeb').val(rowValue.companyWeb);
                    $('#companyLevel').val(rowValue.companyLevel);
                    $('#companyDesc').val(rowValue.companyDesc);
                }
            },

            submit: function () {
                var supplierInfo = {
                    supplierTypeId: $('#supplierType02 option:selected').val(),
                    companyName: $('#companyName02').val(),
                    licenseNumber: $('#licenseNumber').val(),
                    legal: $('#legal').val(),
                    name: $('#name').val(),
                    email: $('#email').val(),
                    phone: $('#phone02').val(),
                    fax: $('#fax').val(),
                    zipCode: $('#zipCode').val(),
                    companyAddress: $('#companyAddress').val(),
                    companyWeb: $('#companyWeb').val(),
                    companyLevel: $('#companyLevel').val(),
                    companyDesc: $('#companyDesc').val()
                };

                var rows = $("#supplierReport").bootstrapTable('getAllSelections');
                if(rows.length == 1) {
                    $.ajax({
                        url: "/supplier/" + rows[0].supplierId + "/one",
                        type: "put",
                        contentType: "application/x-www-form-urlencoded",
                        dataType: "json",
                        data: supplierInfo,
                        success: function (count) {
                            layer.msg('成功修改 [ ' + count + ' ] 个供应商', {icon: 1});
                            supplier.supplierManage.initSupplierReport();
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            var status = xhr.status;
                            layer.alert(
                                '修改供应商信息失败,状态码 [ ' + status + ' ]',
                                {title: '提示框', icon: 0}
                            );
                        }
                    });
                }
            }
        },

        /*移除条件*/
        resetCondition: function () {
            $('#supplierType01 option:first').prop('selected', true);
            // $("#verifyState option[value='s1']").attr("selected", true);
            // $("#schoolName").val('');
            // $("#gender option:selected").val('');
            // $("#profession").val('');
            $("#startDate").val('');
            $("#endDate").val('');
            $("#companyName").val('');
            $("#phone").val('');
        }

    },

    supplierTypeNames: function (select_obj, selectId) {
        $.ajax({
            url: "/supplierType/supplierTypeNameList",
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
    }

}