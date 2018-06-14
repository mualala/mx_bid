package com.meixin.bid_admin.service;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.News;
import com.meixin.bid_admin.web.dto.BasePageCondition;
import com.meixin.bid_admin.web.dto.NewsCondition;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:48 2018/6/4 0004
 */
public interface NewsService {

    int createOneNews(News news);

    JSONObject newsReport(NewsCondition condition);

    int deleteNews(String ids, int uid);

}
