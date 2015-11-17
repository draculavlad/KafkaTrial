package jacob.su.kafka.trial.rpc;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
public class RPCKafkaConsumerThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(RPCKafkaConsumerThread.class);

    private final static AtomicLong threadCount =new AtomicLong(0);

    private String kafkaTopic;
    private ConsumerConnector connector;
    private RPCKafkaConsumer rpcKafkaConsumer;

    public RPCKafkaConsumerThread(String kafkaTopic, ConsumerConnector connector, RPCKafkaConsumer rpcKafkaConsumer) {
        this.kafkaTopic = kafkaTopic;
        this.connector = connector;
        this.rpcKafkaConsumer = rpcKafkaConsumer;
    }

    @Override
    public void run() {
        Long threadCounter = threadCount.incrementAndGet();
        logger.info("Thread Counter is {}", threadCounter);
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(kafkaTopic,1);
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = connector
                        .createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(kafkaTopic);
        for (KafkaStream<byte[], byte[]> stream: streams) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                String message = new String(it.next().message());
                logger.info("Thread Id is {} ,Recieved Message is {}", Thread.currentThread().getId(), message);
                rpcKafkaConsumer.addMessage(message);
                System.out.println(message);
                connector.commitOffsets(true);
            }
        }
        threadCount.decrementAndGet();
    }
}
