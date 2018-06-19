package com.meixin.bid_admin.init;

import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.mappers.dao.AdminDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:14 2018/5/29 0029
 */
@Component
public class AdminDataInitializer {
    private final Logger LOGGER = LoggerFactory.getLogger(AdminDataInitializer.class);

    private final String USER_NAME = "root";
    private final String PASSWORD = "1234";

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void doInit() {
        initAdmin();
    }

    private void initAdmin() {
        Admin admin = adminDao.queryByUsername(USER_NAME);
        if (admin == null) {
            admin = new Admin();
            admin.setUsername(USER_NAME);
            admin.setPassword(passwordEncoder.encode(PASSWORD));
            admin.setName("root");
            admin.addRoles(RoleType.ROLE_ADMIN.name(), RoleType.ROLE_CHECK.name());
            admin.setCreateTime(new Timestamp(System.currentTimeMillis()));
            adminDao.insertSelective(admin);

            LOGGER.info("$$$ 初始化 admin  ..........");
        }
    }


}
