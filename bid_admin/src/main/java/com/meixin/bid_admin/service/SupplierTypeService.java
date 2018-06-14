package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.web.dto.BasePageCondition;

import java.util.List;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:05 2018/5/22 0022
 */
public interface SupplierTypeService {

    SupplierType getSupplierType(String name);

    int createSupplierType(SupplierType supplierType);

    JSONObject getSupplierTypeReport(BasePageCondition condition);

    int deleteSupplierTypes(String ids, int uid) throws Exception;

    int updateSupplierTypeInfo(SupplierType supplierType);

    List<SupplierType> getSupplierTypeNames(int uid);

}
