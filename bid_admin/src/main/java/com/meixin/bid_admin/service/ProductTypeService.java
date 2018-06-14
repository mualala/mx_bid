package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.web.dto.BasePageCondition;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:21 2018/5/18 0018
 */
public interface ProductTypeService {

    ProductType getProductType(String name);

    int createProductType(ProductType productType);

    JSONObject getProductTypeReport(BasePageCondition condition);

    int deleteProductTypes(String ids, int uid) throws Exception;

    int updateProductTypeInfo(ProductType productType);

    List<ProductType> getProductTypeNames(int uid);

}
