package com.example.kafakdemo.kafka.dto;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class RequestDto implements Serializable {
	private String id;
	private String name;

	public String toJson() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
}
