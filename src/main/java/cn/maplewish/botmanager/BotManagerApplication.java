package cn.maplewish.botmanager;

//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Collections;
import java.util.logging.Logger;

//import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

@SpringBootApplication
@EnableDiscoveryClient
public class BotManagerApplication {
    private static final Logger logger = Logger.getLogger(BotManagerApplication.class.getName());

	public static void main(String[] args) {
	    logger.info("Boot main");
		SpringApplication.run(BotManagerApplication.class, args);
	}

	@Bean(name = "basicLogger")
    public Logger getLogger() {
	    return logger;
    }

	@Bean(name = "appRestClient")
    public RestTemplate getRestClient() {
        RestTemplate restClient = new RestTemplate(
                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        // Add one interceptor like in your example, except using anonymous class.
        restClient.setInterceptors(Collections.singletonList((request, body, execution) -> {
            logger.info("Requesting...");
//            LOGGER.debug("Intercepting...");
            return execution.execute(request, body);
        }));

        return restClient;
    }
}
