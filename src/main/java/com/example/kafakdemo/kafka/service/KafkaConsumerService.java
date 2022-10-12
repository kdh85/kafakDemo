package com.example.kafakdemo.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.example.kafakdemo.kafka.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerService {

	@KafkaListener(topicPattern = "exam", groupId = "test1")
	@Retryable(value = Exception.class, maxAttempts = 4, backoff = @Backoff(1000))
	public void consume(String message){
		log.info("Consumed message :{}",message);
		ObjectMapper mapper = new ObjectMapper();
		try{
			ResponseDto responseDto = mapper.readValue(message, ResponseDto.class);
			log.info("responseDto : {}",responseDto);
		}catch (Exception exception){
			log.error("Consumed error : {}",exception.getMessage());
		}
	}

}
