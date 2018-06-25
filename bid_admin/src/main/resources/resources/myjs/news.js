/**
 * Created by yanghm on 2018/6/4 0004.
 */

var news = {

    initNewsReport: function () {
        $('#newsReport').bootstrapTable('destroy').bootstrapTable({
            method: 'post',
            url: '/news/newsReport',
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
            toolbar: '#userBlockTool',
            singleSelect: false,//多选
            exportTypes:[ 'csv', 'txt', 'sql', 'doc', 'excel', 'xlsx', 'pdf'],  //导出文件类型
            // uniqueId: "id", //每一行的唯一标识，一般为主键列
            pagination:true,
            // editable: false,
            sidePagination: 'server',//服务器端请求

            columns: [
                {field: 'state', checkbox: true, width: 30},
                {field: 'newsId', title: 'id', sortable: true, visible: false},
                {field: 'title', title: '新闻标题', sortable: true},
                {field: 'content', title: '新闻内容'},
                {
                    field: 'createTime',
                    title: '创建时间',
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value != null && value != '') {
                            return utils.dateFormat.timeStampToDate(value);
                        }
                    }
                },
                {
                    field: 'updateTime',
                    title: '最后修改时间',
                    sortable: true,
                    visible: false,
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
                    title: $('#title').val(),
                    startDate: $("#startDate").val(),
                    endDate: $("#endDate").val(),
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
            }
        });
    },

    /* 删除产品 */
    deleteNews: function () {
        var rows = $("#newsReport").bootstrapTable('getAllSelections');
        if(rows.length == 0) {
            layer.alert(
                '请选择要删除的新闻',
                {title: '提示框', icon: 0}
            );
        }else {
            //询问框
            layer.confirm('确定要删除选中的 [ ' + rows.length + ' ] 条新闻吗 ?', {
                    btn: ['删除','再考虑一下~~'] //按钮
                }, function(){
                    var ids = '';
                    for(var index in rows) {
                        var id = rows[index].newsId;
                        ids = id + '-' + ids;
                    }
                    $.ajax({
                        url: "/news/" + ids + "/news",
                        type: "delete",
                        contentType: "application/json",
                        dataType: "json",
                        success: function (count) {
                            layer.msg('成功删除了 [ ' + count + ' ] 条新闻', {icon: 1});
                            news.initNewsReport()
                        },
                        error: function (xhr, textStatus, errorThrown) {
                            var status = xhr.status;
                            layer.alert(
                                '删除新闻失败,状态码 [ ' + status + ' ]',
                                {title: '提示框', icon: 0}
                            );
                        }
                    });
                }
            );
        }
    },

}