package ua.kusakabe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LikeProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(LikeProducerApplication.class, args);
    }
}
