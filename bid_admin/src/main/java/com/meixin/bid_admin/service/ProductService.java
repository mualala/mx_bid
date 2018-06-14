package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.web.dto.BasePageCondition;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 13:37 2018/5/14 0014
 */
public interface ProductService {

    /**
     * @Desc:   创建一个产品
     * @Author: yanghm
     * @Param:
     * @Date:   13:40 2018/5/14 0014
     * @Return:
     */
    int createProduct(Product product);

    /**
     * @Desc:   产品管理报表
     * @Author: yanghm
     * @Param:
     * @Date:   16:40 2018/5/15 0015
     * @Return:
     */
    JSONObject productReport(BasePageCondition pageCondition);

    /**
     * @Desc:   删除产品
     * @Author: yanghm
     * @Param:
     * @Date:   15:39 2018/5/16 0016
     * @Return:
     */
    int deleteProducts(String ids, int uid);

    /**
     * @Desc:   更新产品信息
     * @Author: yanghm
     * @Param:
     * @Date:   12:58 2018/5/17 0017
     * @Return:
     */
    int updateProductInfo(Product product);

}
