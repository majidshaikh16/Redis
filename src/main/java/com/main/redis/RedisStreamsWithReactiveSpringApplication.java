package com.main.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 
 * @author Majid Shaikh
 *
 */
@SpringBootApplication(scanBasePackages="com.main.redis")
public class RedisStreamsWithReactiveSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisStreamsWithReactiveSpringApplication.class, args);
	}

}
