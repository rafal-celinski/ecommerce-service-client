package pis24l.projekt.api_client.kafka.model;

public class ProductOrder {
    private String productId;

    public ProductOrder(String productId, int quantity) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    // getters and setters

    @Override
    public String toString() {
        return "ProductOrder{" +
                "productId='" + productId + '\'' +
                '}';
    }
}
