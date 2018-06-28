package com.meixin.bid_admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 1:14 on 2018/2/3 0003 at chengdu.
 */
@EnableGlobalMethodSecurity(jsr250Enabled = true)
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
        http.headers()
                .frameOptions().disable();
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/admin/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
            .and()
            .sessionManagement()
                .invalidSessionUrl("/lockscreen.html")
                .maximumSessions(1)
                .expiredUrl("/login.html")
            .and()
                .and()
            .authorizeRequests()
            // 所有用户均可访问的资源
            .antMatchers("/framework/css/**", "/framework/fonts/**", "/framework/js/**", "/myjs/**", "/images/**",
                    "/lockscreen.html", "/reschedule/**",
                    "/login.html", "/admin/login")
                .permitAll()
            .antMatchers("/index.html").hasAnyRole("USER", "ADMIN", "CHECK", "PROD")
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/checkBidding/**").hasRole("CHECK")
            .antMatchers("/product/**", "/productType/**").hasRole("PROD")
            .antMatchers("/supplier/**", "/supplierType/**").hasRole("PROD")
//                .antMatchers("/supplier/**").hasRole("")

            // 任何尚未匹配的URL只需要验证用户即可访问
            .anyRequest().hasAnyRole("USER", "ADMIN")
//            .authenticated()
            .and()
            .csrf().disable();
    }

}
