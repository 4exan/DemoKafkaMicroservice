package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.kusakabe.payload.Like;
import ua.kusakabe.service.LikeService;

@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);
    private final LikeService likeService;

    @Autowired
    public JsonKafkaConsumer(LikeService likeService) {
        this.likeService = likeService;
    }

    @KafkaListener(topics = "likes", groupId = "likeGroup")
    public void consume(Like payload) {
        LOGGER.info("Message received {}", payload);
        likeService.save(payload);
    }

}
