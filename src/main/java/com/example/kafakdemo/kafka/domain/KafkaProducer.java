package com.example.kafakdemo.kafka.domain;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(final String topic, final String messageKey, final String message) {
		Message<String> messageBuilder = MessageBuilder
			.withPayload(message)
			.setHeader(KafkaHeaders.TOPIC, topic)
			.setHeader(KafkaHeaders.MESSAGE_KEY, messageKey)
			.build();

		kafkaTemplate.send(messageBuilder)
			.addCallback(new ListenableFutureCallback<>() {
				@Override
				public void onSuccess(SendResult<String, String> result) {
					log.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
				}

				@SuppressWarnings("NullableProblems")
				@Override
				public void onFailure(Throwable throwable) {
					log.info("Unable to send message=[{}] due to : {}", message, throwable.getMessage());
				}
			});
	}
}
