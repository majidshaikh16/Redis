
package com.main.redis.config;

import org.springframework.beans.DirectFieldAccessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamReceiver;
import org.springframework.data.redis.stream.StreamReceiver.StreamReceiverOptions;

import com.main.redis.dto.RequestDto;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;

/**
 * @author Majid Shaikh
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
class RedisConfiguration<T> {

	@Bean
	@ConditionalOnProperty("spring.redis.push-messages")
	public StreamReceiver<String, ObjectRecord<String, RequestDto>> streamReceiver(
			ReactiveRedisConnectionFactory factory) {
		return StreamReceiver.create(factory, StreamReceiverOptions.builder().targetType(RequestDto.class).build());
	}

//	@Bean
//	@ConditionalOnProperty("spring.redis.push-messages")
//	public RedisFetchMessageService updater(StreamReceiver<String, ObjectRecord<String, JSONObject>> streamReceiver,
//			RedisReactiveCommands<String, String> commands) {
//		return new RedisFetchMessageService(streamReceiver, commands);
//	}

	@Bean(destroyMethod = "close")
	public StatefulRedisConnection<String, String> connection(ReactiveRedisConnectionFactory factory) {

		DirectFieldAccessor accessor = new DirectFieldAccessor(factory);
		
		RedisClient client = (RedisClient) accessor.getPropertyValue("client");

		return client.connect();
	}

	@Bean
	public RedisReactiveCommands<String, String> commands(StatefulRedisConnection<String, String> connection) {
		return connection.reactive();
	}
}
