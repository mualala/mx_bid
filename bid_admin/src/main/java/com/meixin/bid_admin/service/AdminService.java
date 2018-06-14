package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.web.dto.AdminCondition;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:39 2018/5/14 0014
 */
public interface AdminService {

    Admin checkName(String username);

    JSONObject userReport(AdminCondition adminCondition);

    int deleteUsers(String ids, int uid) throws Exception;

    int createUser(Admin admin);

    int updateUserInfo(Admin admin) throws Exception;

    boolean modifyPwdPreCkeck(String username, String password);



}
