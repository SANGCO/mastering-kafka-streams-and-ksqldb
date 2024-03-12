package dev.sangco.model;

public class Order {

    private String id;
    private String customerId;
    private String product;
    private int quantity;

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "{\"Order\":{"
                + "        \"id\":\"" + id + "\""
                + ",         \"customerId\":\"" + customerId + "\""
                + ",         \"product\":\"" + product + "\""
                + ",         \"quantity\":\"" + quantity + "\""
                + "}}";
    }

}
