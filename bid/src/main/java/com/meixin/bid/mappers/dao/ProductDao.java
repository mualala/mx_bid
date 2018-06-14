package com.meixin.bid.mappers.dao;

import com.meixin.bid.entity.Product;
import com.meixin.bid.mappers.common.IMapper;
import com.meixin.bid.web.dto.BasePageCondition;

import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 10:28 2018/5/14 0014
 */
public interface ProductDao extends IMapper<Product> {

    /**
     * @Desc:   产品管理报表的总记录数
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    int queryProductListTotal(BasePageCondition pageCondition);

    /**
     * @Desc:   产品管理的报表
     * @Author: yanghm
     * @Param:
     * @Date:   08:33 2018/5/17 0017
     * @Return:
     */
    List<Product> queryProductListByUid(BasePageCondition pageCondition);

    /**
     * @Desc:   删除/批量删除 产品
     * @Author: yanghm
     * @Param:
     * @Date:   08:34 2018/5/17 0017
     * @Return:
     */
    int deleteProducts(Map<String, Object> params);

}
