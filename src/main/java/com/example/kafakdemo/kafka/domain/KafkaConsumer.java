package com.example.kafakdemo.kafka.domain;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaConsumer {

	@KafkaListener(topics = "${spring.kafka.template.topic-sample}", containerFactory = "kafkaListenerContainerFactory", groupId = "${spring.kafka.consumer.group-id}")
	public void listenTestTopic(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
		@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) {
		log.info("Topic: [{}] Received Message: [{}] from partition: [{}]", messageKey, message, partition);
	}
}
