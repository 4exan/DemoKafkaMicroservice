package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ua.kusakabe.dto.PostRR;
import ua.kusakabe.payload.Post;
import ua.kusakabe.service.JwtService;

import java.util.Date;

@Service
public class JsonKafkaProducer {

    private final String TOPIC_NAME = "posts";

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private final KafkaTemplate<String, Post> kafkaTemplate;
    private final JwtService jwtService;

    @Autowired
    public JsonKafkaProducer(KafkaTemplate<String, Post> kafkaTemplate, JwtService jwtService) {
        this.kafkaTemplate = kafkaTemplate;
        this.jwtService = jwtService;
    }

    public void sendMessage(PostRR data, String authHeader) {
        Post post = new Post();
        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (!username.isBlank()) {
                post.setUsername(username);
                post.setTitle(data.getTitle());
                post.setContent(data.getContent());
                post.setTimestamp(new Date(System.currentTimeMillis()).toString());
                Message<Post> message = MessageBuilder
                        .withPayload(post)
                        .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                        .build();
                kafkaTemplate.send(message);
                LOGGER.info("Message sent to topic '{}'; Data -> {}", TOPIC_NAME, post.toString());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
