package jacob.su.kafka.trial.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
@Configuration
public class BasicConfig {
    private Logger logger = LoggerFactory.getLogger(BasicConfig.class);

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new MappingJsonFactory(objectMapper());
    }


    @Value("classpath:kafka.properties")
    private Resource kafkaConfigurationResource;

    @Bean(name="kafkaProperties")
    public Properties kafkaProperties() throws IOException {
       		Properties properties = new Properties();
       		if(!kafkaConfigurationResource.exists()){
       			logger.error("Could not find root configuration file.");
       			throw new FileNotFoundException("Root configuration file.");
       		}

       		properties.load(kafkaConfigurationResource.getInputStream());
            return properties;
    }


}
