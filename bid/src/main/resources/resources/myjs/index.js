/**
 * Created by yanghm on 2018/5/10 0010.
 */

//全局注册

// 0. 如果使用模块化机制编程，导入Vue和VueRouter，要调用 Vue.use(VueRouter)
// 1. 定义（路由）组件         可以从其他文件 import 进来
var Main = {template: '#Home'}
var News = {template: '<div>news ........</div>'}
var BiddingNotice = {template: '#biddingNotice'}
var Bidding = {template: '#bidding'}
var myBidding = {template: '#myBidding'}

// 2. 定义路由
// 每个路由应该映射一个组件。 其中"component" 可以是通过 Vue.extend() 创建的组件构造器，或者，只是一个组件配置对象。
// 我们晚点再讨论嵌套路由
var routes = [
    {path:'/', component: Main},
    {path: '/news', component: News},
    {path: '/biddingNotice', component: BiddingNotice},
    {path: '/bidding', component: Bidding},
    {path: '/myBidding', component: myBidding},
];

// 3. 创建 router 实例，然后传 `routes` 配置
// 你还可以传别的配置参数, 不过先这么简单着吧
var router = new VueRouter({
    routes: routes
});

// 4. 创建和挂载根实例。
// 记得要通过 router 配置参数注入路由，从而让整个应用都有路由功能
var app = new Vue({
    data: {
        showName: sessionStorage.getItem('companyName'),
        username: '',
        password: '',
        isSubmit: true, //不能提交
        loginFormToggle: sessionStorage.getItem('loginForm'), //false:显示登录form   true:显示登录成功的信息

        // timer: null,//主页数据定时刷新

        //-------------------------
        mark: '', //竞标单标记
        productList: [],
        showOptsion: false,//默认不显示

        limitPriceTip: '',
        limitPrice: '暂无数据',

        mainTip: '',
        tip1: '',
        tip2: '',

        showTop: false,
        oneProduct: {},//选择出价的产品

        remaindTime: '',
        endTime: '',
        respRemaindTime: 0,

        rank: '暂无数据',
        setPrice: '',

        timer: null,//刷新排名
        //------------------------
    },
    router: router,
    computed: {
    },
    watch: {
        username: function () {
            if (app.username != '' && app.password) app.isSubmit = false
        },
        password: function () {
            if (app.username != '' && app.password) app.isSubmit = false
        },
    },
    methods: {
        goBack: function() {
            window.history.length > 1 ? this.$router.go(-1) : this.$router.push('/')
        },


        login: function () {
            var userInfo = {
                username: app.username,
                password: app.password
            }
            if (!app.isSubmit) {
                app.$axios.post('/supplier/login', userInfo)
                    .then(function (resp) {
                        utils.storage.setSession('companyName', resp.data.data.companyName)
                        utils.storage.setSession('username', resp.data.data.username)
                        app.$router.push('/')
                        // app.$router.go('/')
                        // location.reload();

                        // app.username = ''
                        // app.password = ''
                        app.loginFormToggle = true
                        utils.storage.setSession('loginForm', true)
                    })
                    .catch(function (err) {
                        layer.alert(
                            '登录失败, 账号或密码错误 !',
                            {title: '提示框', icon: 0}
                        );
                    });
            }
        },

        logout: function () {
            layer.confirm('确定注销当前用户吗?', {
                btn: ['注销','不注销'] //按钮
            }, function(){
                sessionStorage.removeItem('loginForm')
                app.loginFormToggle = false
                app.$axios.get('/logout')
                    .then(function (resp) {
                        location.href = '/index.html'
                    })
                    .catch(function (err) {
                        layer.alert(
                            '注销用户失败 !',
                            {title: '提示框', icon: 0}
                        );
                    });
            });
        },

        /*
        autoRefreshReport: function () {
            app.timer = setInterval(function () {
                report.initBidding(0, null)
                report.initBidding(1, null)
                report.initBidding(2, null)
            }, 3500);
        },
        */

        //必须禁掉form默认的submit
        disableFormSubmit: function () {
            return false;
        },

        //****************************************------------------------------***
        clickPriceOpt: function (index) {
            app.showTop = true
            app.oneProduct = app.productList[index]

            app.refreshInfo(1)
            app.autoRefresh()
        },

        autoRefresh: function () {
            if (app.timer === null) {
                app.timer = setInterval(function () {
                    app.refreshInfo(1)
                }, 6000);
            }
        },

        //1=自动刷新  2=点击手动刷新
        refreshInfo: function (type) {
            var la = (type == 2 ? true : false)
            //获取最高或最低出价
            var params = {
                bidName: sessionStorage.getItem('bidName'),
                productId: app.oneProduct.productId,
                mark: app.mark
            }
            $.ajax({
                url: "/bidDetails/lastBidDetail",
                type: "post",
                contentType: "application/x-www-form-urlencoded",
                // dataType: "json",
                data: params,
                success: function (resp) {
                    if (resp.rank != null && resp.rank != '') {
                        app.rank = resp.rank
                    }else {
                        app.rank = '暂无数据'
                    }

                    if (resp.price != null && resp.price != '') {
                        app.price = resp.price
                    }

                    if (resp.limitPrice != null && resp.limitPrice != '') {
                        app.limitPrice = resp.limitPrice
                    }else {
                        app.limitPrice = '暂无数据'
                    }

                    if (resp.endTime != null && resp.endTime != '') {
                        app.endTime = resp.endTime
                        app.respRemaindTime = resp.remaindTime
                        app.computRemaindTime()
                    }
                    if (la) {
                        app.setPrice = ''
                        layer.msg(
                            '数据刷新成功 !',
                            {title: '提示框', icon: 1}
                        );
                    }
                },
                error: function (xhr, textStatus, errorThrown) {
                    var status = xhr.status;
                    layer.alert(
                        '刷新最近一次出价失败,状态码 [ ' + status + ' ]',
                        {title: '提示框', icon: 0}
                    );
                }
            });
            app.computRemaindTime()
        },

        //计算剩余时间
        computRemaindTime: function () {
            // var now = new Date()
            // var diff = new Date(app.endTime) - now
            var diff = app.respRemaindTime
            if (diff <= 0) {
                app.remaindTime = '竞标已结束'
            }else {
                var day = Math.floor(diff / (24 * 3600 * 1000))
                var hour = Math.floor((diff / (3600 * 1000)) % 24)
                var min = Math.floor((diff / (60 * 1000)) % 60)
                var sec = Math.floor((diff / 1000) % 60)

//                    day > 0
//                        ? app.remaindTime = `${day}天`
//                        : app.remaindTime = `${hour}小时 ${min}分 ${sec}秒`
                //上面的写法不兼容ie
                if (day > 0) {
                    app.remaindTime = day + '天'
                }else {
                    app.remaindTime = hour + '小时 ' + min + '分 ' + sec + '秒'
                }
            }
        },

        //出价
        offer: function () {
            var toPrice = true //可以出价请求
            var price = {
                bidName: sessionStorage.getItem('bidName'),
                productId: app.oneProduct.productId,
                price: app.setPrice,
                mark: app.mark
            }
            if(app.setPrice != '') {
                //1=招标（公司卖）   2=竞标（公司买）
                if (app.mark == 1) {
                    if (typeof(app.limitPrice) == 'string') {//第一次出价
//                            console.log('招标 第一次出价 .........')
                        var min = app.oneProduct.maxUnitPrice + app.oneProduct.step
                        if (app.setPrice < min) {
                            toPrice = false
                            layer.alert(
                                '不能出价! 原因: 不能小于 [起拍价+竞价阶梯]',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }else {
//                            console.log('招标 非第一次出价 ......')
                        if ((app.setPrice - app.limitPrice) < app.oneProduct.step) {
                            toPrice = false
                            layer.alert(
                                '不能出价! 原因: (出价 - 最高出价) 不能小于 竞价阶梯',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }
                }
                else if (app.mark == 2) {
                    if (typeof(app.limitPrice) == 'string') {//第一次出价
//                            console.log('竞标 第一次出价 .........')
                        var max = app.oneProduct.maxUnitPrice - app.oneProduct.step
                        if (app.setPrice > max) {
                            toPrice = false
                            layer.alert(
                                '不能出价! 原因: 不能大于 [起拍价-竞价阶梯]',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }else {
//                            console.log('竞标 fei第一次出价 .........')
                        if ((app.limitPrice - app.setPrice) < app.oneProduct.step) {
                            toPrice = false
                            layer.alert(
                                '不能出价! 原因: (上次出价 - 最高出价) 不能小于 竞价阶梯价',
                                {title: '提示框', icon: 0}
                            );
                        }
                    }
                }

                if (toPrice) {
                    layer.confirm('确定出价 ' + price.price + '元 吗?', {
                        btn: ['出价','再想想~~']
                    }, function(){
                        $.ajax({
                            url: "/bidDetails",
                            type: "post",
                            contentType: "application/x-www-form-urlencoded",
                            // dataType: "json",
                            data: price,
                            success: function (resp) {
                                var data = resp.data
                                var code = resp.code
                                var msg = resp.msg

                                switch (code) {
                                    case 1001:
                                        app.refreshInfo() //刷新最高报价和排名
                                        layer.alert(msg,{title: '提示框', icon: 0})
                                        break
                                    case 1002:
                                    case 1003:
                                    case 1004: layer.msg(msg, {title: '提示框', icon: 0}); break;
                                    case 200:
                                        app.rank = data.rank
                                        app.limitPrice = data.limitPrice
                                        app.endTime = data.endTime
                                        app.computRemaindTime()

                                        app.setPrice = ''
                                        layer.msg('出价成功 !', {title: '提示框', icon: 1})
                                        break
                                }
                            },
                            error: function (xhr, textStatus, errorThrown) {
                                var status = xhr.status;
                                layer.alert(
                                    '出价失败! 状态码 [ ' + status + ' ]',
                                    {title: '提示框', icon: 0}
                                );
                            }
                        });
                    });
                }
            }else {
                layer.alert(
                    '出价不能为空 !',
                    {title: '提示框', icon: 0}
                );
            }

        },

        clearProductList: function () {
            app.productList.splice(0, app.productList.length)
        },

        //显示产品详情或出价
        showProductOrPrice: function () {
            var name = sessionStorage.getItem('bidName')
            if (name != undefined) {
                $.ajax({
                    url: "/bidding/" + name + "/bidding",
                    type: "get",
                    success: function (resp) {
                        if (resp[0].status == 1) {
                            app.showOptsion = true
//                        app.remaindTime = app.dateDiffFormat(resp[0].endTime)
                            app.endTime = resp[0].endTime
                            app.respRemaindTime = resp[0].remaindTime

                            app.mark = resp[0].mark
                            if (app.mark == 1) {
                                app.mainTip = ' [招标] 出价提示:'
                                app.tip1 = '1. 第一次出价 [大于等于] 起拍价 + 竞价阶梯'
                                app.tip2 = '2. 非第一次出价 [大于等于] 最高出价 - 竞价阶梯'
                                app.limitPriceTip = '你的最高出价(参考): '
                            }else if (app.mark == 2) {
                                app.mainTip = ' [竞标] 出价提示:'
                                app.tip1 = '1. 第一次出价 [小于等于] 起拍价 - 竞价阶梯'
                                app.tip2 = '2. 非第一次出价 [小于等于] 最高出价 - 竞价阶梯'
                                app.limitPriceTip = '你的最低出价(参考): '
                            }else {
//                            app.tip1 = '标记错误 不能出价'
                            }
                        }
                        for (var i in resp) {
                            var prod = {}
                            var bid = resp[i]
                            prod.productId = bid.product.productId
                            prod.code = bid.product.code
                            prod.name = bid.product.name
                            prod.spec = bid.product.spec
                            prod.number = bid.number
                            prod.unit = bid.product.unit
                            prod.maxUnitPrice = bid.startPrice
                            prod.step = bid.step
                            prod.endDeliveryDate = utils.dateFormat.timeStampToDate(bid.endDeliveryDate)
                            prod.endPayDate = utils.dateFormat.timeStampToDate(bid.endPayDate)

                            app.productList.push(prod)
                            prod = {}
                        }
                    },
                    error: function (xhr, textStatus, errorThrown) {
                        var status = xhr.status;
                        layer.alert(
                            '查询竞标单失败,状态码 [ ' + status + ' ] ',
                            {title: '提示框', icon: 0}
                        );
                    }
                });
            }
        }
        //****************************************-------------------------------***
    },
    updated: function () {
        var username = sessionStorage.getItem('companyName')
        if (username != undefined) {
            report.initBidding(0, null)
            report.initBidding(1, null)
            report.initBidding(2, null)
            // app.autoRefreshReport()

            report.initBidding(0, 0)
            report.initBidding(1, 1)
            report.initBidding(1, 2)

            report.initMyBidding()
            app.showName = sessionStorage.getItem('companyName')
        }
    },
    // vue初始化完时执行
    mounted: function () {
        var username = sessionStorage.getItem('companyName')
        if (username != undefined) {
            report.initBidding(0, null)
            report.initBidding(1, null)
            report.initBidding(2, null)

            report.initBidding(0, 0)
            report.initBidding(1, 1)
            report.initBidding(1, 2)

            report.initMyBidding()
        }


        //***************************************************

        //***************************************************
    },
}).$mount('#app')
