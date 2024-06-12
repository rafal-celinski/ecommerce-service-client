package pis24l.projekt.api_client.kafka.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pis24l.projekt.api_client.kafka.model.OrderResponse;
import pis24l.projekt.api_client.models.Product;
import pis24l.projekt.api_client.models.ProductStatus;
import pis24l.projekt.api_client.repositories.mongo.ProductRepository;
import pis24l.projekt.api_client.services.ProductUpdateService;

import java.util.Optional;

@Service
public class SendProductController {

    private final ProductUpdateService productUpdateService;
    @Autowired
    public SendProductController(ProductUpdateService productUpdateService) {
        this.productUpdateService = productUpdateService;
    }
    @KafkaListener(topics = "product-send", groupId = "group_id")
    public void listen(String ProductId) {
        productUpdateService.updateProductStatus(ProductId, ProductStatus.SENT);
        }
    }
