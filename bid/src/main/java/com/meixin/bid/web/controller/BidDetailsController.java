package com.meixin.bid.web.controller;

import com.meixin.bid.entity.BidDetails;
import com.meixin.bid.service.BidDetailsService;
import com.meixin.bid.web.support.SimpleResponse;
import com.meixin.bid.web.support.Utils;
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
 * @Date： 10:43 2018/6/5 0005
 */
@RestController
@RequestMapping("/bidDetails")
public class BidDetailsController {
    private final Logger LOGGER = LoggerFactory.getLogger(BidDetailsController.class);

    @Autowired
    private BidDetailsService bidDetailsService;

    /**
     * @Desc:   出价操作
     * @Author: yanghm
     * @Param:
     * @Date:   10:45 2018/6/5 0005
     * @Return:
     */
    @PostMapping
    public ResponseEntity price(BidDetails bidDetails, BindingResult errors, HttpSession session) {
        if(errors.hasErrors()) {
            return new ResponseEntity("参数错误", HttpStatus.BAD_REQUEST);
        }

        bidDetails.setUid(Utils.suidFromSession(session));
        bidDetails.setCreateTime(new Timestamp(System.currentTimeMillis()));
        SimpleResponse result = bidDetailsService.price(bidDetails);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/lastBidDetail")
    public ResponseEntity lastPrice(
            @RequestParam("bidName") String bidName,
            @RequestParam("productId") int productId,
            @RequestParam("mark") int mark,
            HttpSession session) {
        BidDetails lastBidDetails = bidDetailsService.lastPrice(bidName, productId, mark,  Utils.suidFromSession(session));
        return ResponseEntity.ok(lastBidDetails);
    }

}
