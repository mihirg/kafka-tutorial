package in.gore.kafka.consumer;

/**
 * Consumer specific constants
 */
public interface ConsumerConstants {

    /**
     * Used to identify which group this consumer belongs to.
     */
    String GROUP_ID_CONFIG = "consumerGroup1";

    /**
     * Used by demo consumer app to bail out if no records are found for some time.
     */
    Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

    /**
     * start from records which are created after the consumer group became active
     */
    String OFFSET_RESET_LATEST = "latest";

    /**
     * TODO: Read documentation on this a bit more.
     * Start from the earliest committed offset.
     */
    String OFFSET_RESET_EARLIER = "earliest";

    /**
     * Max number of records the consumer will fetch in one iteration or poll
     */
    Integer MAX_POLL_RECORDS = 1;
}
