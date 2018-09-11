package in.gore.kafka.producer;

import in.gore.kafka.common.KafkaConstants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.text.MessageFormat;

public class Main {

    public static void main(String[] args) throws Exception {
        try (Producer<Long, String> producer = ProducerCreator.createProducer()) {
            String format = "Record sent offset{0} and partition {1}";
            MessageFormat mf = new MessageFormat(format);
            for (int i = 0; i < ProducerConstants.MESSAGE_COUNT; i++) {
                ProducerRecord<Long, String> record = new ProducerRecord<>(KafkaConstants.TOPIC_NAME, "This is record" + i);
                RecordMetadata metadata = producer.send(record).get();

                System.out.println(mf.format(new Object[] {metadata.offset(), metadata.partition()}));
            }
        }
    }
}
