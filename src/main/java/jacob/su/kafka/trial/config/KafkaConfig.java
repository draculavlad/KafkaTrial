package jacob.su.kafka.trial.config;

import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
@Configuration
@EnableAsync
public class KafkaConfig {

    @Autowired
    @Qualifier("kafkaProperties")
    private Properties kafkaProperties;


    @Bean
    public Producer getProducer() {
        ProducerConfig config = new ProducerConfig(kafkaProperties);
        Producer producer = new Producer(config);
        return producer;
    }

    @Bean
    public ConsumerConnector getConnector(){
        return Consumer.createJavaConsumerConnector(new ConsumerConfig(kafkaProperties));

    }

}
