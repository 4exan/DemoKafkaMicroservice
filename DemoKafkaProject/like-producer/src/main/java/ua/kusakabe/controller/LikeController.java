package ua.kusakabe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.kafka.JsonKafkaProducer;
import ua.kusakabe.payload.Like;

@RestController
@RequestMapping("/like-create")
public class LikeController {

    private final JsonKafkaProducer jsonKafkaProducer;

    public LikeController(JsonKafkaProducer jsonKafkaProducer) {
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

    @GetMapping("/create/{postId}")
    public ResponseEntity<String> publish(@RequestHeader("Authorization") String header, @PathVariable("postId") long postId){
        jsonKafkaProducer.sendMessage(header, postId);
        return ResponseEntity.ok().body("Successfully published!");
    }

}
