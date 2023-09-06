package com.consumer.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CityListener {

    private final Logger logger = Logger.getLogger(CityListener.class.getName());

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    public void listener(String message) {
        logger.info(String.format("Thread: %d", Thread.currentThread().getId()));
        logger.info(String.format("Received from City: %s", message));
    }
}
