package com.meixin.bid_admin.service;


import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.web.dto.SupplierCondition;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:45 2018/5/11 0011
 */
public interface SupplierService {

    Supplier getSupplier(String username);

    /**
     * @Desc:   创建一个供应商
     * @Author: yanghm
     * @Param:
     * @Date:   10:43 2018/5/14 0014
     * @Return:
     */
    int createSupplier(Supplier supplier);

    JSONObject supplierReport(SupplierCondition supplierCondition);

    int deleteSuppliers(String ids, int uid);

    int updateSupplierInfo(Supplier supplier);

}
