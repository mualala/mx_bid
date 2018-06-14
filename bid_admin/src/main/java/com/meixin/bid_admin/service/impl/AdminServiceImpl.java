package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.mappers.dao.AdminDao;
import com.meixin.bid_admin.service.AdminService;
import com.meixin.bid_admin.web.dto.AdminCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:40 2018/5/14 0014
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Admin checkName(String username) {
        return adminDao.queryByUsername(username);
    }

    @Override
    public JSONObject userReport(AdminCondition adminCondition) {
        JSONObject result = new JSONObject();

        adminCondition.pageSettings();
        List<Admin> productList = adminDao.queryUserListByUid(adminCondition);
        result.put("rows", productList);
        result.put("total", adminDao.queryUserListTotal(adminCondition));
        return result;
    }

    @Override
    public int deleteUsers(String ids, int uid) throws Exception {
        try {
            String[] _ids = StringUtils.split(ids, "-");
            List<Integer> idList = new ArrayList<>();
            for(String id : _ids) {
                idList.add(Integer.valueOf(id));
            }
            Map<String, Object> params = new HashMap<>();
            params.put("uids", idList);
            params.put("uid", uid);
            int count = adminDao.deleteUsers(params);
            return count;
        }catch (Exception e) {
            throw new Exception("不能用户,因为有关联的产品 or 供应商， 请先删除相关数据 !!");
        }
    }

    @Override
    public int createUser(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ROLE_USER");
        int count = adminDao.insertSelective(admin);
        return count;
    }

    @Override
    public int updateUserInfo(Admin admin) throws Exception {
        try {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            int count = adminDao.updateByPrimaryKeySelective(admin);
            return count;
        }catch (Exception e) {
            throw new Exception("用户名冲突");
        }
    }

    @Override
    public boolean modifyPwdPreCkeck(String username, String password) {
        Admin admin = adminDao.queryByUsername(username);
        if (admin == null)
            return false;
        return passwordEncoder.matches(password, admin.getPassword());
    }

}
