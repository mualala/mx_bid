package com.meixin.bid_admin.mappers.dao;

import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.entity.ProductType;
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
public interface ProductTypeDao extends IMapper<ProductType> {

    @Select("select * from product_type where name = #{name}")
    ProductType queryByName(@Param("name") String name);

    /**
     * @Desc:   产品类型管理报表的总记录数
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    int queryProductTypeListTotal(BasePageCondition pageCondition);

    /**
     * @Desc:   产品类型管理的报表
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    List<ProductType> queryProductTypeListByUid(BasePageCondition pageCondition);

    /**
     * @Desc:   删除/批量删除 产品类型
     * @Author: yanghm
     * @Param:
     * @Date:   08:34 2018/5/17 0017
     * @Return:
     */
    int deleteProductTypes(Map<String, Object> params);

    /**
     * @Desc:   根据uid得到所有的product type name
     * @Author: yanghm
     * @Param:
     * @Date:   10:35 2018/5/21 0021
     * @Return:
     */
    @Select({"<script>",
            "select * from product_type ",
            "<where><if test='uid != -1'>uid=#{uid}</if></where>",
            "</script>"})
    List<ProductType> queryproductTypeNamesByUid(@Param("uid") int uid);

}
