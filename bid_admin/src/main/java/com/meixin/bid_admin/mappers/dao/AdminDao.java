package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.Admin;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.AdminCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:54 2018/5/14 0014
 */
public interface AdminDao extends IMapper<Admin> {

    @Select("select * from sys_user where username=#{username}")
    Admin queryByUsername(@Param("username") String username);

    @Select("select * from sys_user where username=#{username}")
    Admin Login(@Param("username") String username);

    List<Admin> queryUserListByUid(AdminCondition adminCondition);

    int queryUserListTotal(AdminCondition adminCondition);

    int deleteUsers(Map<String, Object> params);

}
