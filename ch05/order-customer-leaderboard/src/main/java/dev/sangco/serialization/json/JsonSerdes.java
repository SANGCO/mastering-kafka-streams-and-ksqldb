package dev.sangco.serialization.json;

import dev.sangco.model.Customer;
import dev.sangco.model.Order;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

public class JsonSerdes {

    public static Serde<Order> Order() {
        JsonSerializer<Order> serializer = new JsonSerializer<>();
        JsonDeserializer<Order> deserializer = new JsonDeserializer<>(Order.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<Customer> Customer() {
        JsonSerializer<Customer> serializer = new JsonSerializer<>();
        JsonDeserializer<Customer> deserializer = new JsonDeserializer<>(Customer.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

}
