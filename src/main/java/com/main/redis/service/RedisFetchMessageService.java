
package com.main.redis.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamReceiver;
import org.springframework.stereotype.Service;

import com.main.redis.dto.RequestDto;

import io.lettuce.core.api.reactive.RedisReactiveCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * 
 * @author Majid Shaikh
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class RedisFetchMessageService {
	@Autowired
	private StreamReceiver<String, ObjectRecord<String, RequestDto>> streamReceiver;
	
	@Autowired
	private RedisReactiveCommands<String, String> commands;
	
	
	private Disposable subscription;

	@PostConstruct
	private void postConstruct() {
		System.out.println("In post constructor......................");
		Flux<ObjectRecord<String, RequestDto>> feature_poll_stream = streamReceiver
				.receive(StreamOffset.latest("haud-pack"));
		
//		feature_poll_stream.toStream().forEach(System.out::println);

		subscription = feature_poll_stream.flatMap(it -> {
			System.out.println("------------------------------------ ");
			System.out.println("Processing message: " + it);

			RequestDto message = it.getValue();

			return commands.zaddincr("imsirecord",1, message.getImsi());

		}).subscribe();
	}

	@PreDestroy
	private void preDestroy() {
		if (subscription != null) {
			subscription.dispose();
			subscription = null;
		}
	}
}
