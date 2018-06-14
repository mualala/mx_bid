package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.News;
import com.meixin.bid_admin.service.NewsService;
import com.meixin.bid_admin.web.dto.NewsCondition;
import com.meixin.bid_admin.web.dto.ProductCondition;
import com.meixin.bid_admin.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 11:45 2018/6/4 0004
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    private final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(News news, BindingResult errors, HttpSession session) {
        if(errors.hasErrors()) {
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);
        }

        news.setUid(Utils.uidFromSession(session));
        news.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = newsService.createOneNews(news);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/newsReport")
    public JSONObject productReport(NewsCondition condition, HttpSession session) {
        condition.setUid(Utils.uidFromSession(session));
        JSONObject result = newsService.newsReport(condition);
        return result;
    }

    @DeleteMapping("/{ids}/news")
    public ResponseEntity deleteProduct(@PathVariable(name = "ids") String ids, HttpSession session) {
        int count = newsService.deleteNews(ids, Utils.uidFromSession(session));
        return ResponseEntity.ok(count);
    }

}
