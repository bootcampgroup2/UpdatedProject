package com.casestudy.orderservice.controller;


import com.casestudy.orderservice.model.Order;
import com.casestudy.orderservice.kafka.OrderEvent;
import com.casestudy.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderProducer orderProducer;
//    private UserRepository userRepository;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }
    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setEmail("shivani@gmail.com");
//
        orderEvent.setMessage("Order Placed Successfully!" + " Item: " + order.getName() + " Quantity: " + order.getQty() + " Price: " + order.getPrice());
        orderProducer.sendMessage(orderEvent);
        System.out.println("Order Placed!" + orderEvent);
//        Optional<User> userOptional = userRepository.findByEmail("shivani@gmail.com");

//        if (userOptional.isPresent()) {
//            System.out.println("present");
//            User user = userOptional.get();
//            if (user.getNotificationsAllowed()){
//                orderProducer.sendMessage(orderEvent);
//                System.out.println("Notif Send");
//            }else {
//                System.out.println("Notification off!");
//            }
//        }else {
//            System.out.println("User not present");
//        }





        return "Order Placed successfully";


    }
}
