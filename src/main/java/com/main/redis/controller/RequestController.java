
package com.main.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.main.redis.dto.RequestDto;
import com.main.redis.service.RedisPushMessageService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @author Majid Shaikh
 */
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class RequestController {
	@Autowired
	private RedisPushMessageService messageService;

	@PostMapping("/address/add")
	public Mono<RecordId> addAddress(@RequestBody RequestDto request) {
		return messageService.push(request);
	}

}
