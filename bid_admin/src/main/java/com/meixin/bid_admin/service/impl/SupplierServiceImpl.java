package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.entity.Supplier;
import com.meixin.bid_admin.mappers.dao.SupplierDao;
import com.meixin.bid_admin.service.SupplierService;
import com.meixin.bid_admin.web.dto.SupplierCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int createSupplier(Supplier supplier) {
        supplier.setPassword(passwordEncoder.encode(supplier.getPassword()));
        int count = supplierDao.insert(supplier);
        return count;
    }

    @Override
    public JSONObject supplierReport(SupplierCondition supplierCondition) {
        JSONObject result = new JSONObject();

        supplierCondition.pageSettings();
        List<Supplier> supplierList = supplierDao.querySupplierListByUid(supplierCondition);
        result.put("rows", supplierList);
        result.put("total", supplierDao.querySupplierListTotal(supplierCondition));
        return result;
    }

    @Override
    public int deleteSuppliers(String ids, int uid) {
        String[] _ids = StringUtils.split(ids, "-");
        List<Integer> idList = new ArrayList<>();
        for(String id : _ids) {
            idList.add(Integer.valueOf(id));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("supplierIds", idList);
        params.put("uid", uid);
        int count = supplierDao.deleteSuppliers(params);
        return count;
    }

    @Override
    public int updateSupplierInfo(Supplier supplier) {
        int count = supplierDao.updateByPrimaryKeySelective(supplier);
        return count;
    }

    @Override
    public int updateSupplierPassword(int sid, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("sid", sid);
        params.put("newPassword", passwordEncoder.encode(password));
        params.put("updateTime", new Timestamp(System.currentTimeMillis()));
        return supplierDao.updateSupplierPassword(params);
    }

}
