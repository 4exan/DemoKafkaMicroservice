package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ua.kusakabe.payload.Like;
import ua.kusakabe.service.JwtService;

@Service
public class JsonKafkaProducer {
    private final String TOPIC_NAME = "likes";

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private final KafkaTemplate<String, Like> kafkaTemplate;
    private final JwtService jwtService;

    @Autowired
    public JsonKafkaProducer(KafkaTemplate<String, Like> kafkaTemplate, JwtService jwtService) {
        this.kafkaTemplate = kafkaTemplate;
        this.jwtService = jwtService;
    }

    public void sendMessage(String header, Long postID) {
        Like like = new Like();
        try {
            String username = jwtService.extractUsername(header.substring(7));
            if (!username.isBlank()) {
                like.setUsername(username);
                like.setPost_id(postID);
                Message<Like> message = MessageBuilder
                        .withPayload(like)
                        .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                        .build();
                kafkaTemplate.send(message);
                LOGGER.info("Message sent to topic '{}'; Data -> {}", TOPIC_NAME, like.toString());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
