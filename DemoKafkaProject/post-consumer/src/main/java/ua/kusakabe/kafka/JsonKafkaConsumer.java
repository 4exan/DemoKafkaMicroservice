package ua.kusakabe.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.kusakabe.payload.Post;
import ua.kusakabe.service.PostService;

@Service
public class JsonKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);
    private final PostService postService;

    @Autowired
    public JsonKafkaConsumer(PostService postService) {
        this.postService = postService;
    }

    @KafkaListener(topics = "posts", groupId = "postGroup")
    public void consume(Post payload) {
        LOGGER.info("Message received {}", payload);
        postService.save(payload);
    }

}
