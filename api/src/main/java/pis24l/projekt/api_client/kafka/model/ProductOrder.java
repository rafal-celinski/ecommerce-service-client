package pis24l.projekt.api_client.kafka.model;

public class ProductOrder {
    private String productId;
    private int quantity;

    public ProductOrder(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // getters and setters

    @Override
    public String toString() {
        return "ProductOrder{" +
                "productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
