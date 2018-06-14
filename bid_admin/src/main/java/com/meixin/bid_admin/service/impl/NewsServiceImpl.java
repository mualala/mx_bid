package com.meixin.bid_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.News;
import com.meixin.bid_admin.entity.Product;
import com.meixin.bid_admin.mappers.dao.NewsDao;
import com.meixin.bid_admin.service.NewsService;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import com.meixin.bid_admin.web.dto.NewsCondition;
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
 * @Date： 11:48 2018/6/4 0004
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public int createOneNews(News news) {
        int count = newsDao.insertSelective(news);
        return count;
    }

    @Override
    public JSONObject newsReport(NewsCondition condition) {
        JSONObject result = new JSONObject();

        condition.pageSettings();
        List<Product> productList = newsDao.queryNewsListByUid(condition);
        result.put("rows", productList);
        result.put("total", newsDao.queryNewsListTotal(condition));
        return result;
    }

    @Override
    public int deleteNews(String ids, int uid) {
        String[] _ids = StringUtils.split(ids, "-");
        List<Integer> idList = new ArrayList<>();
        for(String id : _ids) {
            idList.add(Integer.valueOf(id));
        }
        Map<String, Object> params = new HashMap<>();
        params.put("newsIds", idList);
        params.put("uid", uid);
        int count = newsDao.deleteNews(params);
        return count;
    }

}
