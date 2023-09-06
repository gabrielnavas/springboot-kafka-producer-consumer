package com.producer.controllers;

import com.producer.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("city")
public class CityController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public ResponseEntity<Object> createCity(
            @RequestBody City city
    ) {
        city.setId(UUID.randomUUID());
        kafkaTemplate.send("topic-1", city.toString());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
