package com.meixin.bid_admin.security.authentication;

import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.mappers.dao.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 3:48 on 2018/2/4 0004 at chengdu.
 */
@Component
public class AdminDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("login username="+username  );

        Admin admin = adminDao.queryByUsername(username);
        if (admin == null)
            throw new UsernameNotFoundException("用户名不存在");

        return admin;
    }


}
