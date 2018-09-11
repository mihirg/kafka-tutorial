package in.gore.kafka.common;

/**
 * Constants common to consumer and producer
 */
public interface KafkaConstants {

    /**
     * Kafka broker(s) address. If kafka is running in cluster mode, then provide comma separated address list
     */
    String KAFKA_BROKERS = "localhost:9092";

    String TOPIC_NAME = "test";

}
