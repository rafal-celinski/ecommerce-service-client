package pis24l.projekt.api_client.kafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import pis24l.projekt.api_client.kafka.model.ProductOrder;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final KafkaTemplate<String, ProductOrder> kafkaTemplate;
    @Autowired
    public OrderController(KafkaTemplate<String, ProductOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final String TOPIC = "product_orders";

    @PostMapping
    public String placeOrder(@RequestBody ProductOrder productOrder) {
        kafkaTemplate.send(TOPIC, productOrder);
        return "Order placed successfully!";
    }
}
