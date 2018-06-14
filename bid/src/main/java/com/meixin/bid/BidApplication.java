package com.meixin.bid;

import com.meixin.bid.web.support.SimpleResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BidApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidApplication.class, args);
	}


	@GetMapping("/session/invalid")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse sessionInvalid() {
		return new SimpleResponse(HttpStatus.UNAUTHORIZED.value(), "session失效");
	}

}
