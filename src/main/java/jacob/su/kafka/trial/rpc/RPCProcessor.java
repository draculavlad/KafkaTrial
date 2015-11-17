package jacob.su.kafka.trial.rpc;

import java.util.List;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
@Component
public class RPCProcessor {
    @Value("#{kafkaProperties['kafka.topic']}")
    private String kafkaTopic;

    @Value("#{kafkaProperties['kafka.messsage.key']}")
    private String kafkaTopicKey;

    @Autowired
    private RPCKafkaConsumer consumer;

    @Autowired
    private Producer producer;

    public void sendMsg(String message) {
        KeyedMessage<String, String> data =
                new KeyedMessage<String, String>(kafkaTopic, message);
        producer.send(data);
    }

    public List<String> getMsg() {
        List<String> list = consumer.getMessages();
        return list;
    }
}
