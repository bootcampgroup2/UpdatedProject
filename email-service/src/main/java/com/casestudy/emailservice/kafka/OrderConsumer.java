

    
package com.casestudy.emailservice.kafka;




import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.casestudy.emailservice.models.Ordermail;
import com.casestudy.emailservice.repositories.OrdermailRepository;
import com.casestudy.orderservice.kafka.OrderEvent;
import com.casestudy.orderservice.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.RegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@Service
public class OrderConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration config;

    @Autowired
    private OrdermailRepository ordermailRepository;


    @KafkaListener(topics = "order_topics",groupId = "${spring.kafka.consumer.group-id}")
    public  void  consume(String jsonMessage) throws MessagingException {
//        ordermailRepository.save(new Ordermail(event.getEmail(), event.toString(),false,"high"));


//            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
        ObjectMapper objectMapper = new ObjectMapper();
        OrderEvent event = null;
//        Order order;

        try {
            event  = objectMapper.readValue(jsonMessage, OrderEvent.class);
            ordermailRepository.save(new Ordermail(event.getEmail(), event.toString(), false, "high"));
            System.out.println(event);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error deserializing message: " + e.getMessage());
        }
//        LOGGER.info("Received OrderEvent: {}", event);
//            LOGGER.info(event.toString());
//            System.out.println(event);
//            ordermailRepository.save(new Ordermail(event.getEmail(), event.toString(), false, "high"));

//        for (Ordermail ordermail: ordermailRepository.findOne()){
//            System.out.println(ordermail);
//        }

        Map<String,Object> model = new HashMap<>();
    	model.put("message",event.getMessage());
//    	model.put("orderId",event.getOrderId());
//    	model.put("name", order.getName());
//    	model.put("quantity",event.getOrder().getQty());
//    	model.put("price",event.getOrder().getPrice());
////
//        if(event.getIsInstantEmail()){
//            LOGGER.info("Order event recieved in email service => %models",event.toString());
            simpleEmail(event.getEmail(),event,"Order",model);
//            System.out.println(event.getEmail());
            System.out.println("Called simpleemail!!!");
//        }else{
//            System.out.println("Send email to user subscribed to an event");
//        }
//        LOGGER.info("Order event recieved in email service => %models",event.toString());
//        try {
//            // Sleep for 1 minute (60,000 milliseconds)
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            // Handle the InterruptedException
//            // For example, you can log the interruption and decide how to proceed
//            Thread.currentThread().interrupt();  // Reset the interrupted status
//        }
////        simpleEmail("shivanii2607@gmail.com",event,"Order");
//        System.out.println("Called simpleemail!!!");

    }

    @KafkaListener(topics = "user_topics", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeUser(String registerMessage) throws JsonProcessingException, MessagingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RegisterEvent event = objectMapper.readValue(registerMessage, RegisterEvent.class);
        LOGGER.info("Received OrderEvent: {}", event);
        ordermailRepository.save(new Ordermail(event.getEmail(), event.toString(),false,"low"));

        Map<String,Object> model = new HashMap<>();
        model.put("message",event.getMessage());
//        simpleEmail(event.getEmail(),event,"Registeration Successful",model);


//        System.out.println("Received message from user_topic: " + message);

    }

    public void simpleEmail(String toEmail, OrderEvent body, String Subject, Map<String,Object> model) throws MessagingException{


    	MimeMessage message = mailSender.createMimeMessage();
    	try {
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        // add attachment
    //    helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

        Template t = config.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        helper.setFrom("p87773623@gmail.com");
        helper.setTo(toEmail);
        helper.setText(html,true);
        helper.setSubject(Subject);

        mailSender.send(message);


        System.out.println("Mail Send....");
    	}
    	catch(IOException|TemplateException e) {
    		System.out.println("Failed");
    	}

    }
}

