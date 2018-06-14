package com.meixin.bid.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 7:47 on 2018/2/4 0004 at chengdu.
 */
@ControllerAdvice
//controller的所有异常都进入这里处理
public class ControllerExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handlerExcption() {
        Map map = new HashMap();
        map.put(1, "用户名不存在");
        return map;
    }

}
