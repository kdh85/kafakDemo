package com.example.kafakdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.example.kafakdemo.kafka.domain.KafkaConsumer;
import com.example.kafakdemo.kafka.domain.KafkaProducer;
import com.example.kafakdemo.kafka.dto.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@EmbeddedKafka(
	partitions = 1,
	brokerProperties = {
		"listeners=PLAINTEXT://localhost:9092"
	},
	ports = {
		9092
	}
)
public class EmbeddedKafkaIntegrationTest {

	@Autowired
	private KafkaProducer kafkaProducer;

	@Autowired
	private KafkaConsumer kafkaConsumer;

	@Autowired
	private ObjectMapper mapper;

	@Value("${spring.kafka.template.topic-sample}")
	private String TOPIC;

	@Test
	void test() throws JsonProcessingException, InterruptedException {
		RequestDto requestDto = RequestDto.builder()
			.id("1")
			.name("test")
			.build();

		String payload = mapper.writeValueAsString(requestDto);
		String messageKey = "test_key1";
		kafkaProducer.sendMessage(TOPIC, messageKey, payload);

		Thread.sleep(1000);

		kafkaConsumer.listenTestTopic(payload, 0, messageKey);
	}
}
