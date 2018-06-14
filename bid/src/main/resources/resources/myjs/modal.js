/**
 * Created by yanghm on 2018/5/10 0010.
 */
var modalsVue = new Vue({
    el: '#modals',
    data: {
        user: {},

    },
    methods: {
        login: function() {
            // var loginParams = new URLSearchParams();
            // loginParams.append('username', this.username);
            // loginParams.append('password', this.password);

            console.log(modalsVue.user)
            this.$axios.post('/supplier/login', modalsVue.user)
                .then(function (resp) {
                    utils.storage.setSession('username', modalsVue.user.username)
                    console.log(app.$router)
                    app.$router.push('/')
                    // app.$router.go('/')
                    // location.reload();
                })
                .catch(function (err) {
                    // alert('服务器内部错误')
                    console.log(22222222222222222222)
                    console.log(err)
                });
            this.username = '';
            this.password = '';
        }
    }
});
