package in.gore.kafka.consumer;

import in.gore.kafka.common.KafkaConstants;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;


public class ConsumerCreator {

    public static Consumer<Long, String> createConsumer() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, ConsumerConstants.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, ConsumerConstants.MAX_POLL_RECORDS);

        /**
         * When a consumer from a group receives a message it must commit the offset of that record. If this config
         * is set to <code>true</code>, then periodically offsets will be committed, but for production level, this
         * should be false and offset should be committed manually.
         */
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        /**
         * For each consumer group, the last committed offset value is stored. This configuration is use full if no offset
         * is committed for that group, i.e. the group is newly created. Setting it to "earliest" will cause the consumer to
         * fetch records from the beginning of offset, i.e. from zero
         *
         * Setting the value to "latest" will cause the consumer to fetch only those records which have been created after the
         * consumer group became active.
         */
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, ConsumerConstants.OFFSET_RESET_EARLIER);

        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(KafkaConstants.TOPIC_NAME));
        return consumer;

    }
}
