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

    @PostMapping()
    public ResponseEntity reschedule(@RequestParam("bidName") String bidName) {
        System.err.println("22222222");
        ResponseEntity result = rescheduleService.reschedule(bidName);
        return result;
    }

}
