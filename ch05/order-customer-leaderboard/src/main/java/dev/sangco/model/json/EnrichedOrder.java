package dev.sangco.model.json;

import dev.sangco.model.Customer;
import dev.sangco.model.Order;

public class EnrichedOrder {

    private Order order;

    private Customer customer;

    public EnrichedOrder(Order order, Customer customer) {
        this.order = order;
        this.customer = customer;
    }

    public Order getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "EnrichedOrder{" +
                "order=" + order +
                ", customer=" + customer +
                '}';
    }

}

