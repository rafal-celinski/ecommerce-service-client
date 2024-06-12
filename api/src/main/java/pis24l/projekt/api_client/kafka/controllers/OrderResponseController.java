package pis24l.projekt.api_client.kafka.controllers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.kafka.model.OrderResponse;

@Service
public class OrderResponseController {

    @KafkaListener(topics = "order_responses", groupId = "group_id")
    public void listen(OrderResponse orderResponse) {
        //
    }
}
