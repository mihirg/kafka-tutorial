package in.gore.kafka.producer;

public interface ProducerConstants {

    /**
     * max number of messages to be generated by the sample producer app
     */
    Integer MESSAGE_COUNT = 1000;

    /**
     * Id of the producer for kafka to determine source of the request.
     */
    String CLIENT_ID = "client1";
}
