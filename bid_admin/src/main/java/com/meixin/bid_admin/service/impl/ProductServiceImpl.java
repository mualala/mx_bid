package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.mappers.dao.ProductDao;
import com.meixin.bid_admin.service.ProductService;
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
 * @Date： 13:41 2018/5/14 0014
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public int createProduct(Product product) {
        int count = productDao.insert(product);
        return count;
    }

    @Override
    public JSONObject productReport(BasePageCondition pageCondition) {
        JSONObject result = new JSONObject();

        pageCondition.pageSettings();
        List<Product> productList = productDao.queryProductListByUid(pageCondition);
        result.put("rows", productList);
        result.put("total", productDao.queryProductListTotal(pageCondition));
        return result;
    }

    @Override
    public int deleteProducts(String ids, int uid) {
        String[] _ids = StringUtils.split(ids, "-");
        List<Integer> idList = new ArrayList<>();
        for(String id : _ids) {
            idList.add(Integer.valueOf(id));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("productIds", idList);
        params.put("uid", uid);
        int count = productDao.deleteProducts(params);
        return count;
    }

    @Override
    public int updateProductInfo(Product product) {
        int count = productDao.updateByPrimaryKeySelective(product);
        return count;
    }


}
