package com.meixin.bid;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class BidApplicationTests {

	@Test
	public void contextLoads() {
		String aa = "ROLE_USER,ROLE_CHECK,";
		System.err.println(aa.contains("ROLE_USER"));
		System.err.println(aa.indexOf("ROLE_USER"));
		System.err.println(aa.substring("ROLE_USER".length()));

	}

}
