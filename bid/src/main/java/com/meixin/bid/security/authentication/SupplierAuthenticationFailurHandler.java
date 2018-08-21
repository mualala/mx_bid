package com.meixin.bid.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.web.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 5:53 on 2018/2/4 0004 at chengdu.
 */
@Component
public class SupplierAuthenticationFailurHandler implements AuthenticationFailureHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(SupplierAuthenticationFailurHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        LOGGER.error("登录失败,用户ID:{} 用户名:{}", "未知", request.getParameter("username"));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        System.err.println(exception.getMessage());
        response.getWriter().write(JSONObject.toJSONString(new ResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR)));
    }

}
