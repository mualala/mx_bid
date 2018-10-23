package com.meixin.bid_admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid_admin.entity.WinBid;
import com.meixin.bid_admin.service.WinBidService;
import com.meixin.bid_admin.web.dto.BidDetailsCondition;
import com.meixin.bid_admin.web.dto.WinBidCondition;
import com.meixin.bid_admin.web.support.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 09:25 2018/6/11 0011
 */
@RestController
@RequestMapping("/winBid")
public class WinBidController {
    private final Logger LOGGER = LoggerFactory.getLogger(WinBidController.class);

    @Autowired
    private WinBidService winBidService;

    @PostMapping
    public ResponseEntity winBid(@Valid WinBid winBid, BindingResult errors, HttpSession session) {
        if(errors.hasErrors()) {
            return new ResponseEntity("必填数据项有空", HttpStatus.BAD_REQUEST);
        }
        winBid.setUid(Utils.uidFromSession(session));
        winBid.setCreateTime(new Timestamp(System.currentTimeMillis()));
        int count = winBidService.winBid(winBid);
        return ResponseEntity.ok(count);
    }

    @PostMapping("/doBidDetailsReport")
    public JSONObject bidDetailsReport(WinBidCondition condition, HttpSession session) {
        condition.setUid(Utils.uidFromSession(session));
        // 审核权限可以看所有的中标结果
        boolean ischeck = (boolean) session.getAttribute("ischeck");
        if (ischeck) {
            condition.setUid(-1);
        }

        JSONObject result = winBidService.doBidDetailsReport(condition);
        return result;
    }


}
