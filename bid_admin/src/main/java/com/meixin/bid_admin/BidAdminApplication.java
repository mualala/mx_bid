package com.meixin.bid_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = {"com.meixin.bid_admin.mybatis.dao"})
public class BidAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidAdminApplication.class, args);
	}


}
