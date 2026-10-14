package com.gautam.concurra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConcurraApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConcurraApplication.class, args);
	}

}
