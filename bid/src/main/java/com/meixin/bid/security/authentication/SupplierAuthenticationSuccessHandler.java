package com.meixin.bid.security.authentication;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.entity.Supplier;
import com.meixin.bid.web.support.SimpleResponse;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Supplier supplier = (Supplier) authentication.getPrincipal();

        WebRequest webRequest = new ServletWebRequest(request);
        SessionAttributeStore sessionStore = new DefaultSessionAttributeStore();
        sessionStore.storeAttribute(webRequest, "uid", supplier.getUid());
        sessionStore.storeAttribute(webRequest, "suid", supplier.getSupplierId());

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONObject.toJSONString(ResponseEntity.ok("登录成功")));
    }

}
