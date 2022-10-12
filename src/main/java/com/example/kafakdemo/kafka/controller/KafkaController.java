package com.example.kafakdemo.kafka.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafakdemo.kafka.dto.RequestDto;
import com.example.kafakdemo.kafka.service.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "kafka")
@RequiredArgsConstructor
public class KafkaController {
	private final KafkaProducerService producer;

	@PostMapping
	public String sendMessage(@RequestParam("message") String message) {
		this.producer.sendMessage(message);

		return "success";
	}

	@PostMapping("/test")
	public String sendDto(@RequestBody RequestDto requestDto) throws JsonProcessingException {
		this.producer.sendMessage(requestDto.toJson());
		return "success";
	}
}
