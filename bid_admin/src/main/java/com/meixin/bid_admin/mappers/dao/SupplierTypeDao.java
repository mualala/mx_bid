package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.mappers.common.IMapper;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:22 2018/5/18 0018
 */
public interface SupplierTypeDao extends IMapper<SupplierType> {

    @Select("select * from supplier_type where name = #{name}")
    SupplierType queryByName(@Param("name") String name);

    /**
     * @Desc:   供应商管理报表的总记录数
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    int querySupplierTypeListTotal(BasePageCondition pageCondition);

    /**
     * @Desc:   供应商管理的报表
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    List<SupplierType> querySupplierTypeListByUid(BasePageCondition pageCondition);

    /**
     * @Desc:   删除/批量删除 供应商
     * @Author: yanghm
     * @Param:
     * @Date:   08:34 2018/5/17 0017
     * @Return:
     */
    int deleteSupplierTypes(Map<String, Object> params);

    /**
     * @Desc:   根据uid得到所有的supplier type name
     * @Author: yanghm
     * @Param:
     * @Date:   10:35 2018/5/21 0021
     * @Return:
     */
    @Select({"<script>",
            "select * from supplier_type ",
//            "<where><if test='uid != -1'>uid=#{uid}</if></where>",
            "</script>"})
    List<SupplierType> querySupplierTypeNamesByUid(@Param("uid") int uid);

}
