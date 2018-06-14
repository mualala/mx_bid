package com.meixin.bid.service.impl;

import com.meixin.bid.entity.Supplier;
import com.meixin.bid.mappers.dao.SupplierDao;
import com.meixin.bid.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:46 2018/5/11 0011
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierDao supplierDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Supplier getSupplier(String username) {
        Supplier supplier = supplierDao.queryByUsername(username);
        return supplier;
    }

    @Override
    public boolean modifyPwdPreCkeck(String username, String password) {
        Supplier supplier = supplierDao.queryByUsername(username);
        if (supplier == null)
            return false;
        return passwordEncoder.matches(password, supplier.getPassword());
    }

    @Override
    public int updateSupplierInfo(Supplier supplier) throws Exception {
        try {
            supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
            int count = supplierDao.updateByPrimaryKeySelective(supplier);
            return count;
        }catch (Exception e) {
            throw new Exception("供应商名称冲突");
        }
    }

}
