package com.meixin.bid.service;

import com.meixin.bid.entity.Supplier;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:45 2018/5/11 0011
 */
public interface SupplierService {

    Supplier getSupplier(String username);

    boolean modifyPwdPreCkeck(String username, String password);

    int updateSupplierInfo(Supplier supplier) throws Exception;

}
