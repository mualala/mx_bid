package com.meixin.bid.security.authentication;

import com.meixin.bid.entity.Supplier;
import com.meixin.bid.mappers.dao.SupplierDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author yanghm
 * @Description
 * @Created by 下午 3:48 on 2018/2/4 0004 at chengdu.
 */
@Component
public class SupplierDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Supplier supplier = supplierDao.queryByUsername(username);
        if(supplier == null)
            throw new UsernameNotFoundException("用户名不存在");

        return supplier;

//        return new User(String.valueOf(supplier.getUid()), supplier.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }


}
