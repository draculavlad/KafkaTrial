package jacob.su.kafka.trial.rpc;

import java.util.*;
import java.util.concurrent.ExecutorService;

import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
@Configuration
public class RPCKafkaConsumer {


    @Value("#{kafkaProperties['kafka.topic']}")
    private String kafkaTopic;

    private static final List<String> recievedMessages = Collections.synchronizedList(new ArrayList<String>());
    private boolean jobStatus = false;


    @Autowired
    private ConsumerConnector connector;

    @Bean
    public ExecutorService getExecutor(){
        ExecutorService executorService = newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i=0; i<Runtime.getRuntime().availableProcessors();i++){
            Runnable job = new RPCKafkaConsumerThread(kafkaTopic, connector, this);
            executorService.submit(job);
        }
        return executorService;
    }

    private synchronized void launchJobIfNotRunning(){
        if(jobStatus==false) {
            jobStatus = true;
        }
    }


    public void addMessage(String message){
        recievedMessages.add(message);
    }

    public List<String> getMessages() {
        return recievedMessages;
    }



}
