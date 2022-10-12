package com.example.kafakdemo.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import lombok.extern.slf4j.Slf4j;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

	@Value("${spring.kafka.consumer.bootstrap-servers}")
	private String BOOTSTRAP_SERVER_ADDRESS;

	@Value("${spring.kafka.consumer.auto-offset-reset}")
	private String AUTO_OFFSET_RESET;

	@Value("${spring.kafka.consumer.enable-auto-commit}")
	private String AUTO_COMMIT;

	@Bean
	public ConsumerFactory<String,String> consumerFactory(){

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_ADDRESS);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, AUTO_COMMIT);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String>kafkaListenerContainerFactory(){
		ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		kafkaListenerContainerFactory.setConcurrency(2);
		kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());
		return kafkaListenerContainerFactory;
	}
}
