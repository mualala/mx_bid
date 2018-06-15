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
        showName: sessionStorage.getItem('username'),
        username: '',
        password: '',
        isSubmit: true, //不能提交
        loginFormToggle: sessionStorage.getItem('loginForm'), //false:显示登录form   true:显示登录成功的信息


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
                        console.log(resp)
                        utils.storage.setSession('username', resp.data.data)
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


        //必须禁掉form默认的submit
        disableFormSubmit: function () {
            return false;
        },
    },
    updated: function () {
        report.initBidding(0, null)
        report.initBidding(1, null)
        report.initBidding(2, null)

        report.initBidding(0, 0)
        report.initBidding(1, 1)
        report.initBidding(1, 2)

        app.showName = sessionStorage.getItem('username')
    },
    // vue初始化完时执行
    mounted: function () {
        report.initBidding(0, null)
        report.initBidding(1, null)
        report.initBidding(2, null)

        report.initBidding(0, 0)
        report.initBidding(1, 1)
        report.initBidding(1, 2)

        // $('#m_username').val(sessionStorage.getItem('username'))
    },
}).$mount('#app')