package pis24l.projekt.api_client.kafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.kafka.model.OrderResponse;
import pis24l.projekt.api_client.models.ProductStatus;
import pis24l.projekt.api_client.services.ProductUpdateService;

@Service
public class OrderResponseController {
    private final ProductUpdateService productUpdateService;

    @Autowired
    public OrderResponseController(ProductUpdateService productUpdateService) {
        this.productUpdateService = productUpdateService;
    }
    @KafkaListener(topics = "order_responses", groupId = "group_id")
    public void listen(OrderResponse orderResponse) {
        if (orderResponse.getStatus().equals("SUCCESS")) {
            productUpdateService.updateProductStatus(orderResponse.getProductId(), ProductStatus.SOLD);
        } else {
            productUpdateService.updateProductStatus(orderResponse.getProductId(), ProductStatus.NOT_AVAILABLE);
        }
    }
}
