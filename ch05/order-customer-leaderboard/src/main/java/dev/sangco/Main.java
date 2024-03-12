package dev.sangco;

import dev.sangco.model.Customer;
import dev.sangco.model.Order;
import dev.sangco.model.json.EnrichedOrder;
import dev.sangco.serialization.json.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
//        ktable_ktable_join();
        kstream_ktable_join();
    }

    private static void ktable_ktable_join() {
        StreamsBuilder builder = new StreamsBuilder();

        // 주문 테이블 생성
        KTable<String, Order> orders = builder.table("orders", Consumed.with(Serdes.String(), JsonSerdes.Order()));

        // 고객 테이블 생성
        KTable<String, Customer> customers = builder.table("customers", Consumed.with(Serdes.String(), JsonSerdes.Customer()));

        // 주문과 고객 정보를 조인
        ValueJoiner<Order, Customer, EnrichedOrder> orderCustomerJoiner = (order, customer) -> new EnrichedOrder(order, customer);
        KTable<String, EnrichedOrder> enrichedOrders = orders.join(customers, orderCustomerJoiner);

        // 풍부한 주문 정보를 출력하기 위해 toStream()을 사용하고, print()로 출력
        enrichedOrders.toStream().print(Printed.<String, EnrichedOrder>toSysOut().withLabel("enriched-orders"));

        // 스트림 시작
        KafkaStreams streams = new KafkaStreams(builder.build(), getStreamsConfig());
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        streams.start();
    }

    private static void kstream_ktable_join() {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Order> ordersStream = builder.stream("orders", Consumed.with(Serdes.String(), JsonSerdes.Order()));

        KTable<String, Customer> customersTable = builder.table("customers", Consumed.with(Serdes.String(), JsonSerdes.Customer()));

        ValueJoiner<Order, Customer, EnrichedOrder> orderCustomerJoiner = (order, customer) -> new EnrichedOrder(order, customer);
        KStream<String, EnrichedOrder> enrichedOrdersStream = ordersStream.join(customersTable, orderCustomerJoiner, Joined.with(Serdes.String(), JsonSerdes.Order(), JsonSerdes.Customer()));

        enrichedOrdersStream.print(Printed.<String, EnrichedOrder>toSysOut().withLabel("enriched-orders"));

        // 스트림 시작
        KafkaStreams streams = new KafkaStreams(builder.build(), getStreamsConfig());
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        streams.start();
    }

    private static Properties getStreamsConfig() {
        String host = "localhost";
        Integer port = 7070;
        String stateDir = "./tmp/kafka-streams";
        String endpoint = String.format("%s:%s", host, port);

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "dev");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        props.put(StreamsConfig.APPLICATION_SERVER_CONFIG, endpoint);
        props.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        return props;
    }

}
