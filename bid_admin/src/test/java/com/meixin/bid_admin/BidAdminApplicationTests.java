//package com.meixin.bid_admin;
//
//import com.alibaba.fastjson.JSONObject;
//import com.meixin.bid_admin.entity.Product;
//import com.meixin.bid_admin.mappers.dao.ProductDao;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class BidAdminApplicationTests {
//
//	@Autowired
//	private ProductDao productDao;
//
//	@Test
//	public void contextLoads() {
//		Product product = new Product();
//		product.setUid(2);
//		product.setMaxUnitPrice(100f);
//		product.setCreateTime(new Timestamp(System.currentTimeMillis()));
//		int a = productDao.insert(product);
//		System.err.println(a);
//
//		List<Product> b = productDao.selectAll();
//		System.err.println(JSONObject.toJSON(b));
//	}
//
//	@Test
//	public void a() {
//		String uuid = UUID.randomUUID().toString();
//		System.err.println(uuid.replace("-", ""));
//		System.err.println(Integer.MAX_VALUE);
//		System.err.println(String.valueOf(Integer.MAX_VALUE).length());
//	}
//
//
//}
