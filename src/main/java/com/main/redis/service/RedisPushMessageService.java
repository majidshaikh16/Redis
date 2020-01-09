package com.main.redis.service;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import com.main.redis.dto.RequestDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Majid Shaikh
 */
@Component
@RequiredArgsConstructor
public class RedisPushMessageService {

	private final ReactiveRedisTemplate<String, String> template;

	public Mono<RecordId> push(RequestDto message) {
		try {
			return template.opsForStream().add(ObjectRecord.create("haud-pack", message));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		System.out.print("Failed to parse the message.......................");
		return null;
	}
}
