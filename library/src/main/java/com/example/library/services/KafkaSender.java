package com.example.library.services;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import com.example.library.dtos.AuthorDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaSender {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${kafka.topic.name}")
	private String topicName;
	
	public void sendData( AuthorDto author) {
		Map<String, Object> headers = new HashMap<>();
		headers.put(KafkaHeaders.TOPIC, topicName);
		kafkaTemplate.send(new GenericMessage<AuthorDto>(author, headers));
		log.info("Data - " + author.toString() + "send to kafka topic : " + topicName);
	}
}
