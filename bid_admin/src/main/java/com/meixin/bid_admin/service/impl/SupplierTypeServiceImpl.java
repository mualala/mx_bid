package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.mappers.dao.SupplierTypeDao;
import com.meixin.bid_admin.service.SupplierService;
import com.meixin.bid_admin.service.SupplierTypeService;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:05 2018/5/22 0022
 */
@Service
public class SupplierTypeServiceImpl implements SupplierTypeService {

    @Autowired
    private SupplierTypeDao supplierTypeDao;

    @Override
    public SupplierType getSupplierType(String name) {
        SupplierType supplierType = supplierTypeDao.queryByName(name);
        return supplierType;
    }

    @Override
    public int createSupplierType(SupplierType supplierType) {
        int count = supplierTypeDao.insert(supplierType);
        return count;
    }

    @Override
    public JSONObject getSupplierTypeReport(BasePageCondition condition) {
        JSONObject result = new JSONObject();

        condition.pageSettings();
        List<SupplierType> productTypeList = supplierTypeDao.querySupplierTypeListByUid(condition);
        result.put("rows", productTypeList);
        result.put("total", supplierTypeDao.querySupplierTypeListTotal(condition));
        return result;
    }

    @Override
    public int deleteSupplierTypes(String ids, int uid) throws Exception {
        try {
            String[] _ids = StringUtils.split(ids, "-");
            List<Integer> idList = new ArrayList<>();
            for(String id : _ids) {
                idList.add(Integer.valueOf(id));
            }
            Map<String, Object> params = new HashMap<>();
            params.put("supplierTypeIds", idList);
            params.put("uid", uid);
            int count = supplierTypeDao.deleteSupplierTypes(params);
            return count;
        }catch (Exception e) {
            throw new Exception("不能删除供应商类型,因为有关联的供应商， 请先删除对应供应商 !!");
        }
    }

    @Override
    public int updateSupplierTypeInfo(SupplierType supplierType) {
        int count = supplierTypeDao.updateByPrimaryKeySelective(supplierType);
        return count;
    }

    @Override
    public List<SupplierType> getSupplierTypeNames(int uid) {
        List<SupplierType> supplierTypeNames = supplierTypeDao.querySupplierTypeNamesByUid(uid);
        return supplierTypeNames;
    }


}
