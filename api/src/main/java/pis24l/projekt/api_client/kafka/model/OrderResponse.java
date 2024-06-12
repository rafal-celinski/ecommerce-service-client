package pis24l.projekt.api_client.kafka.model;

public class OrderResponse {
    private String productId;
    private String status;
    private String message;

    public OrderResponse() {}

    public OrderResponse(String productId, String status, String message) {
        this.productId = productId;
        this.status = status;
        this.message = message;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "productId='" + productId + '\'' +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
