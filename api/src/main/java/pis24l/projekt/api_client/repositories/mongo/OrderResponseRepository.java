package pis24l.projekt.api_client.repositories.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pis24l.projekt.api_client.kafka.model.OrderResponse;

public interface OrderResponseRepository extends MongoRepository<OrderResponse, String> {
}
