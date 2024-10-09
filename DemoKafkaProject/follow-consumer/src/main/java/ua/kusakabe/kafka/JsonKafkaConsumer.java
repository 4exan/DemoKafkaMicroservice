package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.kusakabe.payload.Follow;
import ua.kusakabe.service.FollowService;

@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);
    private final FollowService followService;

    @Autowired
    public JsonKafkaConsumer(FollowService followService) {
        this.followService = followService;
    }

    @KafkaListener(topics = "follows", groupId = "followGroup")
    public void consume(Follow payload) {
        LOGGER.info("Message received {}", payload);
        followService.save(payload);
    }

}
