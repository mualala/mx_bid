package com.meixin.bid_admin.service;

import org.springframework.http.ResponseEntity;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 16:34 2018/6/22 0022
 */
public interface RescheduleService {

    ResponseEntity reschedule(String bidName);

}
