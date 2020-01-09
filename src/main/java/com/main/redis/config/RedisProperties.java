
package com.main.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author Majid Shaikh
 */
@ConfigurationProperties("spring.redis")
@Data
public class RedisProperties {
	private boolean pushMessages;
}
