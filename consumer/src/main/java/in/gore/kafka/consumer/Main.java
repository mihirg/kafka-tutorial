package in.gore.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * Assumes that the topic is created with two partitions. Since we have two consumers in the consumer group and two
 * partitions, each consumer should receive message from exactly one partition.
 *
 */

public class Main {

    public static void main(String[] args) throws Exception {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(2);

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executor.submit(getCallable(1, start, end));
        executor.submit(getCallable(2, start, end));

        // kickoff the threads
        start.countDown();
        end.await();
    }

    private static Callable<Void> getCallable(final int threadId, final CountDownLatch start, final CountDownLatch end) {
        return new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try (Consumer<Long, String> consumer = ConsumerCreator.createConsumer()) {
                    int numofNoRecordPolls = 0;

                    // wait till we are given a go ahead.
                    start.await();
                    while(true) {
                        // 1000 is the timeout
                        ConsumerRecords<Long, String> records = consumer.poll(1000);
                        if (records.count() == 0) {
                            numofNoRecordPolls++;
                            if (numofNoRecordPolls > ConsumerConstants.MAX_NO_MESSAGE_FOUND_COUNT) {
                                break;
                            }
                        }

                        String format = "Record Key: {0}, Record Value: {1}, Record Offset: {2}, Record Partition: {3} in thread id: " + threadId;
                        MessageFormat mf = new MessageFormat(format);
                        records.forEach(e -> {
                            System.out.println(mf.format(new Object[] {e.key(), e.value(), e.offset(), e.partition()}));
                        });

                        // commits the offset of record to the broker
                        consumer.commitAsync();
                    }
                    end.countDown();
                }
                return null;

            }
        };
    }
}
