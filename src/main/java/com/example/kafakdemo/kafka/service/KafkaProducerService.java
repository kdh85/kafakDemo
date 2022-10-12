package com.example.kafakdemo.kafka.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(final String payload) {
		log.info("Produce payload :{}", payload);
		this.kafkaTemplate.send("exam", payload);
	}
}
