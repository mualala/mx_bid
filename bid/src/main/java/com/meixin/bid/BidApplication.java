package com.meixin.bid;

import com.alibaba.fastjson.JSONObject;
import com.meixin.bid.web.support.SimpleResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@SpringBootApplication
public class BidApplication {

	@Resource
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BidApplication.class, args);
	}

	@GetMapping("/aa")
	public ResponseEntity sessionInvalid() {
		System.err.println("11111111");
		ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8082/test-rest", String.class);
		System.err.println(JSONObject.toJSONString(responseEntity));
		return responseEntity;
	}

}
