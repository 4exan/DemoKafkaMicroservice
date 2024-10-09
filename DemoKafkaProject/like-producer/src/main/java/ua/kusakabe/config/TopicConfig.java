package ua.kusakabe.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic topic() {
        return new NewTopic("likes", 2, (short) 1);
    }

}
