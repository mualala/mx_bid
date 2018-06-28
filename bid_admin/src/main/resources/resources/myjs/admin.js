

var admin = {

    userManage: {
        initUserReport: function () {
            $('#userReport').bootstrapTable('destroy');//先销毁表格

            //初始化表格，动态从服务器加载数据
            $('#userReport').bootstrapTable({
                method: 'post',
                url: '/admin/adminReport',
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
                    {field: 'uid', title: '用户id', sortable: true, visible: false},
                    {field: 'username', title: '账号', sortable: true},
                    {field: 'name', title: '名字'},
                    {
                        field: 'role',
                        title: '权限',
                        formatter: function (value, row, index) {
                            if(value != null && value != '') {
                                if (value.match('ROLE_ADMIN')) {
                                    return '超级管理员'
                                }
                                if (value.match('ROLE_CHECK') && value.match('ROLE_PROD')) {
                                    return '产品管理和审核'
                                }
                                if (value.match('ROLE_CHECK')) {
                                    return '采购和审核'
                                }
                                if (value.match('ROLE_PROD')) {
                                    return '产品管理'
                                }
                                if (value.match('ROLE_USER')) {
                                    return '采购'
                                }
                            }
                        }
                    },
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
                        sortName: params.sortName,
                        sortOrder: params.sortOrder,
                    };
                    if (user != undefined) {
                        param.username = user.queryCondition.username
                        param.startDate = user.queryCondition.startDate
                        param.endDate = user.queryCondition.endDate
                    }
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

        /* 删除用户 */
        deleteUser: function () {
            var rows = $("#userReport").bootstrapTable('getAllSelections');
            if(rows.length == 0) {
                layer.alert(
                    '请选择要删除的用户',
                    {title: '提示框', icon: 0}
                );
            }else {
                //询问框
                layer.confirm('确定要删除选中的 [ ' + rows.length + ' ] 个用户吗 ?', {
                        btn: ['删除','再考虑一下~~'] //按钮
                    }, function(){
                        var del = true
                        var ids = '';
                        for(var index in rows) {
                            var id = rows[index].uid;
                            ids = id + '-' + ids;

                            if(rows[index].name == 'root') {
                                console.log(rows[index].name)
                                del = false; break;
                            }
                        }
                        if(del) {
                            $.ajax({
                                url: "/admin/" + ids + "/admin",
                                type: "delete",
                                contentType: "application/json",
                                dataType: "json",
                                success: function (count) {
                                    layer.msg('成功删除了 [ ' + count + ' ] 个用户', {icon: 1});
                                    admin.userManage.initUserReport()
                                },
                                error: function (xhr, textStatus, errorThrown) {
                                    var status = xhr.status;
                                    layer.alert(
                                        '删除用户失败,状态码 [ ' + status + ' ], 原因: ' + xhr.responseText,
                                        {title: '提示框', icon: 0}
                                    );
                                }
                            });
                        }else {
                            layer.alert(
                                '不能删除选中的 root 用户 !!',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }
                );
            }
        },

        // 修改用户信息
        modifyUser: {
            toggleModal: function () {
                var rows = $("#userReport").bootstrapTable('getAllSelections');
                if(rows.length == 0) {
                    layer.alert(
                        '请选择要修改的用户!',
                        {title: '提示框', icon: 0}
                    );
                }else if (rows.length > 1) {
                    layer.alert(
                        '一次只能修改一个用户!',
                        {title: '提示框', icon: 0}
                    );
                }else {
                    $('#modifyUserModal').modal('toggle');
                    var rowValue = rows[0];
                    $('#m_username').val(rowValue.username);
                    // $('#m_password').val(rowValue.password);
                    $('#m_name').val(rowValue.name);
                    if (rowValue.name == 'root') {
                        $('#m_name_dis').attr('disabled', 'disabled')
                    }
                }
            },

            submit: function () {
                var rows = $("#userReport").bootstrapTable('getAllSelections');
                if(rows.length == 1) {
                    var userInfo = {
                        username: $('#m_username').val(),
                        password: $('#m_password').val(),
                        name: $('#m_name').val(),
                        uid: rows[0].uid
                    };
                    if ($('#checkAuth').is(':checked')) userInfo.checkAuth = $('#checkAuth').val()
                    if ($('#prodAuth').is(':checked')) userInfo.prodAuth = $('#prodAuth').val()

                    if (userInfo.name == 'root') {
                        layer.alert('不能修改 root 名称', {title: '提示框', icon: 0})
                    }else {
                        if ($('#m_password').val() != '') {
                            $.ajax({
                                url: "/admin/one",
                                type: "put",
                                contentType: "application/x-www-form-urlencoded",
                                dataType: "json",
                                data: userInfo,
                                success: function (count) {
                                    layer.msg('成功修改 [ ' + count + ' ] 个用户', {icon: 1});
                                    admin.userManage.initUserReport()
                                },
                                error: function (xhr, textStatus, errorThrown) {
                                    var status = xhr.status;
                                    layer.alert(
                                        '修改用户信息失败,状态码 [ ' + status + ' ], 原因: ' + xhr.responseText,
                                        {title: '提示框', icon: 0}
                                    );
                                }
                            });
                        }else {
                            layer.alert(
                                '密码不能为空 !',
                                {title: '提示框', icon: 0}
                            );
                        }

                    }
                }
            }
        },

    }

}