echo "Waiting for Kafka to come online..."

cub kafka-ready -b kafka:9092 1 20

# create the orders events topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic orders \
  --replication-factor 1 \
  --partitions 4 \
  --create

# create the customers topic
kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic customers \
  --replication-factor 1 \
  --partitions 4 \
  --create

sleep infinity
