package com.producer.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.internals.Topic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerKafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;


    /**
     * This method return the configuration of the producer.
     * @return DefaultKafkaProducerFactory of the configurations map.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        final Map<String, Object> configs = new HashMap<>();

        // setting the endpoint of the kafka
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());

        // serialize the keys with string
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // serialize the values with string
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configs);
    }

    /**
     * This method return the template using the producerConfiguration.
     * @return KafkaTemplate with the key type String and value type String.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * This method return the kafka admin for create topics begin my producer app.
     * @return KafkaAdmin with the configs.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }


    /**
     * Things to create a topic
     * @return
     */
    @Bean
    public NewTopic topic1() {
        // Opção 1: Definir na aplicação a quantidade de partitions e o replication factor
        final String topicName = "topic-1";
        final int partitions = 10;
        final short replicationFactor = Short.parseShort("1");
        return new NewTopic(topicName, partitions, replicationFactor);

    // Opção 2: deixar que o broker defina a quantidade de partitions e o replication factor
    //        return TopicBuilder.name("topic-1").build();
    }


    /**
     * To create the N topics
     * @return
     */
//    @Bean
//    public KafkaAdmin.NewTopics topics() {
//        return new KafkaAdmin.NewTopics(
//                TopicBuilder.name("topic-1").build(),
//                TopicBuilder.name("topic-2").build(),
//                TopicBuilder.name("topic-3").build()
//        );
//    }
}
