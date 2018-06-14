/**
 * Created by yanghm on 2018/5/14 0014.
 */
var axios_instance =
    axios.create({
    // config里面有这个transformRquest，这个选项会在发送参数前进行处理。
    // 这时候我们通过Qs.stringify转换为表单查询参数
    transformRequest: [function (data) {
        data = Qs.stringify(data);
        return data;
    }],
    // 设置Content-Type
    headers:{'Content-Type':'application/x-www-form-urlencoded'}
});

// 配置统一拦截器
//请求拦截器
axios_instance.interceptors.request.use(function(req) {
    //在请求发出之前进行一些操作
    console.log('req ok')
    return req;
}, function(err) {
    console.log('req err')
    //Do something with request error
    return Promise.reject(error);
});

//响应拦截器
axios_instance.interceptors.response.use(function(resp){
    console.log('resp ok')
    //在这里对返回的数据进行处理
    return resp;
},function(err){
    var resp = err.response;
    var status = resp.status;
    alert(status)
    if(status >= 300 && status < 400) {
        console.log('服务器响应3xx ('+status+') 状态码');
    }else if(status >= 400 && status < 500) {
        console.log('服务器响应4xx ('+status+') 状态不可用');
    }else if(status >= 500) {
        console.log('服务器响应5xx ('+status+') 状态,服务器内部错误！！！');
    }
    return Promise.reject(err);
});

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'

// 注册http的全局配置
Vue.prototype.$axios = axios_instance;
