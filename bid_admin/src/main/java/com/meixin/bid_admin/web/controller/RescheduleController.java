package com.meixin.bid_admin.web.controller;

import com.meixin.bid_admin.service.RescheduleService;
import com.meixin.bid_admin.task.BiddingTaskUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 16:31 2018/6/22 0022
 */
@RestController
@RequestMapping("/reschedule")
public class RescheduleController {

    @Autowired
    private RescheduleService rescheduleService;

    //produces = "application/json;charset=utf-8"
    @PutMapping("/{bidName}")
    public ResponseEntity reschedule(@PathVariable("bidName") String bidName) {
        ResponseEntity result = rescheduleService.reschedule(bidName);
        return result;
    }

}
