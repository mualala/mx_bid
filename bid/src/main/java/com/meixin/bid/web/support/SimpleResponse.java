package com.meixin.bid.web.support;

import org.springframework.http.HttpStatus;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 13:21 2018/5/11 0011
 */
public class SimpleResponse {

    private int code;

    private String msg;

    private Object data;

    public SimpleResponse(String msg) {
        this.msg = msg;
    }

    public SimpleResponse(int code, String desc) {
        this(code, desc, null);
    }

    public SimpleResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static SimpleResponse ERROR() {
        return new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作失败");
    }

    public static SimpleResponse ERROR(String msg) {
        return new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

    public static SimpleResponse ERROR(String msg, Object data) {
        return new SimpleResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, data);
    }

    public static SimpleResponse OK() {
        return SimpleResponse.OK("操作成功");
    }

    public static SimpleResponse OK(String msg) {
        return SimpleResponse.OK(msg, null);
    }

    public static SimpleResponse OK(Object data) {
        return SimpleResponse.OK(null, data);
    }

    public static SimpleResponse OK(String msg, Object data) {
        return new SimpleResponse(HttpStatus.OK.value(), msg, data);
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
