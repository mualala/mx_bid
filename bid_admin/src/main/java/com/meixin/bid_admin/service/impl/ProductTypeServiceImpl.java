package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.ProductType;
import com.meixin.bid_admin.entity.SupplierType;
import com.meixin.bid_admin.mappers.dao.ProductTypeDao;
import com.meixin.bid_admin.service.ProductTypeService;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 17:22 2018/5/18 0018
 */
@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    
    private final Logger LOGGER = LoggerFactory.getLogger(ProductTypeServiceImpl.class);

    @Autowired
    private ProductTypeDao productTypeDao;

    @Override
    public ProductType getProductType(String name) {
        ProductType productType = productTypeDao.queryByName(name);
        return productType;
    }

    @Override
    public int createProductType(ProductType productType) {
        int count = productTypeDao.insert(productType);
        return count;
    }

    @Override
    public JSONObject getProductTypeReport(BasePageCondition condition) {
        JSONObject result = new JSONObject();

        condition.pageSettings();
        List<ProductType> productTypeList = productTypeDao.queryProductTypeListByUid(condition);
        result.put("rows", productTypeList);
        result.put("total", productTypeDao.queryProductTypeListTotal(condition));
        return result;
    }

    @Override
    public int deleteProductTypes(String ids, int uid) throws Exception {
        try {
            String[] _ids = StringUtils.split(ids, "-");
            List<Integer> idList = new ArrayList<>();
            for(String id : _ids) {
                idList.add(Integer.valueOf(id));
            }
            Map<String, Object> params = new HashMap<>();
            params.put("productTypeIds", idList);
            params.put("uid", uid);
            int count = productTypeDao.deleteProductTypes(params);
            return count;
        }catch (Exception e) {
            throw new Exception("不能删除产品类型,因为有关联的产品， 请先删除相关产品 !!");
        }
        
    }

    @Override
    public int updateProductTypeInfo(ProductType productType) {
        int count = productTypeDao.updateByPrimaryKeySelective(productType);
        return count;
    }

    @Override
    public List<ProductType> getProductTypeNames(int uid) {
        List<ProductType> productTypeNames = productTypeDao.queryproductTypeNamesByUid(uid);
        return productTypeNames;
    }


}
