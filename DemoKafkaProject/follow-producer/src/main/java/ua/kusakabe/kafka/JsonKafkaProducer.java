package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ua.kusakabe.payload.Follow;
import ua.kusakabe.service.JwtService;

@Service
public class JsonKafkaProducer {
    private final String TOPIC_NAME = "follows";

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private final KafkaTemplate<String, Follow> kafkaTemplate;
    private final JwtService jwtService;

    @Autowired
    public JsonKafkaProducer(KafkaTemplate<String, Follow> kafkaTemplate, JwtService jwtService) {
        this.kafkaTemplate = kafkaTemplate;
        this.jwtService = jwtService;
    }

    public void sendMessage(String followed, String header) {
        Follow follow = new Follow();
        try {
            String follower = jwtService.extractUsername(header.substring(7));
            follow.setFollower(follower);
            follow.setFollowed(followed);
            Message<Follow> message = MessageBuilder
                    .withPayload(follow)
                    .setHeader(KafkaHeaders.TOPIC, TOPIC_NAME)
                    .build();
            kafkaTemplate.send(message);
            LOGGER.info("Message sent to topic '{}'; Data -> {}", TOPIC_NAME, follow);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
