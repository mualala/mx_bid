/**
 * Created by yanghm on 2018/5/17 0015.
 */
var utils = {
    storage: {
        //seesion级别缓存
        setSession: function(_key, _value){
            try{
                sessionStorage.setItem(_key, _value);
            }catch(e){console.log("username storage failed");}
        },
        getSession: function(_key){
            var sessionVal = sessionStorage.getItem(_key);
            return sessionVal;
        },
        delSession: function(_key){
            sessionStorage.removeItem(_key);
        },

        //持久化缓存
        setLocalDB: function(_key, _value){
            try{
                localStorage.setItem(_key, _value);
            }catch(e){console.log("token storage failed");}
        },
        getLocalDB: function(_key){
            var LocalDBVal = localStorage.getItem(_key);
            return LocalDBVal;
        },
        delLocalDB: function(_key){
            localStorage.removeItem(_key);
        }
    },

    dateFormat: {
        /*
         * 给input设置日期控件
         * param _obj是jQuery对象
         */
        initDate: function (_obj) {
            _obj.datepicker({
                todayHighlight: true,//高亮今天的日期
                clearBtn: true,
                autoclose: true,
                // locale: 'zh-CN',
                language: 'zh-CN',
                showButtonPanel: true,//显示底部菜单
                changeMonth: true,//月选择项
                changeYear: true,//年选择项
                format: 'yyyy-mm-dd'
            });
        },

        /*
         * 将时间戳装换成yyyy-MM-dd HH-mm-ss的日期格式
         */
        timeStampToDate: function (_timestamp) {
            if (_timestamp != null && _timestamp != '') {
                var _date = new Date(_timestamp);
                var year = _date.getFullYear();

                var month = _date.getMonth() + 1;
                month = utils.dateFormat.append0(month);

                var day = _date.getDate();
                day = utils.dateFormat.append0(day)

                var hour = _date.getHours();
                hour = utils.dateFormat.append0(hour);

                var minute = _date.getMinutes();
                minute = utils.dateFormat.append0(minute)

                var second = _date.getSeconds();
                second = utils.dateFormat.append0(second);

                return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            }
        },

        append0: function (_obj) {
            return _obj < 10 ? "0" + _obj : _obj;
        },

        dateDiffFormat: function(_timestamp){
            var timeDiff = '';
            var date = new Date();
            var diff = new Date(_timestamp) - date
            if (diff / (24 * 3600 * 1000) >= 1) {
                timeDiff = Math.floor(cur_time / (24 * 3600 * 1000)) + '天';
            } else if (diff / (3600 * 1000) >= 1) {
                timeDiff = Math.floor(cur_time / (3600 * 1000)) + '小时';
            } else if (diff / 60 * 1000 >= 1) {
                timeDiff = Math.ceil(cur_time / (60 * 1000)) + '分钟';
            } else {
                timeDiff = Math.ceil(cur_time / 1000) + '秒';
            }
            return '大约' + timeDiff;
        },
    },


}
