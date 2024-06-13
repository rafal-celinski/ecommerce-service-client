package pis24l.projekt.api_client.kafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order/{id}")
public class OrderController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    public OrderController(KafkaTemplate<String, String> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }

    private static final String TOPIC = "product_orders";

    @PostMapping
    public void placeOrder(@PathVariable String id) {
        kafkaTemplate.send(TOPIC, id);
    }
}
