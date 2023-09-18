package com.casestudy.orderservice.kafka;

import com.casestudy.orderservice.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProducer.class);
    private NewTopic topic;

    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC_NAME = "order_topics";

    public OrderProducer(NewTopic topic) {
        this.topic = topic;
    }
    @Autowired
    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(OrderEvent event){
        LOGGER.info("Order event => %models", event.toString());
        Message<OrderEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                .build();
        kafkaTemplate.send(message);

//        OrderEvent event = new OrderEvent();
//        event.setEmail("r3550510@gmail.com");
//        event.setMessage("Order Placed Successfully!" + " Item: " + order.getName()+ "  Quantity: " + order.getQty() +"  Price: " + order.getPrice());
//        System.out.println(event);
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonMessage;
//        try {
//            jsonMessage = objectMapper.writeValueAsString(event);
//        } catch (JsonProcessingException e) {
//            // Handle JSON serialization error
//            LOGGER.error("Error serializing message to JSON: " + e.getMessage());
//            return; // Exit the method if there's an error
//        }
//        LOGGER.info("OrderEvent => %models", jsonMessage);
//        kafkaTemplate.send(TOPIC_NAME, jsonMessage);
//        System.out.println(jsonMessage);
//        LOGGER.info("Order event => %models", event.toString());
//        Message<OrderEvent> message = MessageBuilder
//                .withPayload(event)
//                .setHeader(KafkaHeaders.TOPIC,topic.name())
//                .build();
//        kafkaTemplate.send(message);
    }
}
