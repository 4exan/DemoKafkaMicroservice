package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.kafka.JsonKafkaProducer;

@RestController
@RequestMapping("/follow-create")
public class FollowController {

    private final JsonKafkaProducer jsonKafkaProducer;

    @Autowired
    public FollowController(JsonKafkaProducer jsonKafkaProducer) {
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

    @GetMapping("/{username}")
    public ResponseEntity<String> followUser(@RequestHeader("Authorization") String header, @PathVariable("username") String username){
        jsonKafkaProducer.sendMessage(username, header);
        return ResponseEntity.ok("User '" + username + "' successfully followed!");
    }

}
