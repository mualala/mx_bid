package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:44 2018/5/11 0011
 */
public interface SupplierDao extends IMapper<Supplier> {

    @Select("select * from supplier where username = #{username}")
    Supplier queryByUsername(@Param("username") String username);

    /**
     * @Desc:   供应商管理报表的总记录数
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    int querySupplierListTotal(BasePageCondition pageCondition);

    /**
     * @Desc:   供应商管理的报表
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    List<Supplier> querySupplierListByUid(BasePageCondition pageCondition);

    /**
     * @Desc:   删除/批量删除 供应商
     * @Author: yanghm
     * @Param:
     * @Date:   08:34 2018/5/17 0017
     * @Return:
     */
    int deleteSuppliers(Map<String, Object> params);

    /**
     * 更新供应商密码
     * @param params
     * @return
     */
    int updateSupplierPassword(Map<String, Object> params);

}
