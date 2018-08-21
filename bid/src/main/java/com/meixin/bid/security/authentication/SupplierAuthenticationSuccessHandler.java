package com.meixin.bid.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.entity.Supplier;
import com.meixin.bid.web.support.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 5:14 on 2018/2/4 0004 at chengdu.
 */
@Component
class SupplierAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(SupplierAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Supplier supplier = (Supplier) authentication.getPrincipal();

        WebRequest webRequest = new ServletWebRequest(request);
        SessionAttributeStore sessionStore = new DefaultSessionAttributeStore();
        sessionStore.storeAttribute(webRequest, "uid", supplier.getUid());
        sessionStore.storeAttribute(webRequest, "suid", supplier.getSupplierId());

        LOGGER.info("登录成功,用户ID:{} 用户名:{}", supplier.getUid(), supplier.getUsername());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(SimpleResponse.OK("登录成功", supplier)));
    }

}
