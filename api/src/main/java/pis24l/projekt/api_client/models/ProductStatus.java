package pis24l.projekt.api_client.models;

public enum ProductStatus {
    UP("Up"),
    SOLD("Sold"),
    SENT("Sent"),
    NOT_AVAILABLE("Not Available");

    private final String value;

    ProductStatus(String value) {
        this.value = value;
    }
}
