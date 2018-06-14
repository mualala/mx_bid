package com.meixin.bid.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 1:14 on 2018/2/3 0003 at chengdu.
 */
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.formLogin()
                    .loginPage("/index.html")
                    .loginProcessingUrl("/supplier/login")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and()
                .sessionManagement()
                    .invalidSessionUrl("/lockscreen.html")
                    .maximumSessions(1)
                    .expiredUrl("/index.html")
                .and()
                .and()
                .authorizeRequests()
                // 所有用户均可访问的资源
                .antMatchers("/framework/css/**", "/framework/fonts/**", "/framework/js/**", "/myjs/**", "/images/**",
                        "/lockscreen.html",
                        "/index.html", "/supplier/login")
                    .permitAll()
                // ROLE_USER的权限才能访问的资源
                .antMatchers("/user/**").hasRole("USER")
                // 任何尚未匹配的URL只需要验证用户即可访问
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }

}
