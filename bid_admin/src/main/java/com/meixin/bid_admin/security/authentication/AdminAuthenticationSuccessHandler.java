package com.meixin.bid_admin.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.bind.support.SessionAttributeStore;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 5:14 on 2018/2/4 0004 at chengdu.
 */
@Component
class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(AdminAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Admin admin = (Admin) authentication.getPrincipal();

        WebRequest webRequest = new ServletWebRequest(request);
        SessionAttributeStore sessionStore = new DefaultSessionAttributeStore();
        sessionStore.storeAttribute(webRequest, "uid",
                admin.getRole().contains("ROLE_ADMIN") ? -1 : admin.getUid());

        sessionStore.storeAttribute(webRequest, "ischeck",
                admin.getRole().contains("ROLE_CHECK") ? true : false);

        LOGGER.info("登录成功,用户ID:{} 用户名:{}", admin.getUid(), admin.getUsername());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(ResponseEntity.ok("登录成功")));

//        response.sendRedirect("/index.html");
    }

}
