package com.meixin.bid_admin;

import com.meixin.bid_admin.task.BiddingTaskUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@MapperScan(basePackages = {"com.meixin.bid_admin.mybatis.dao"})
public class BidAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidAdminApplication.class, args);
	}

}
