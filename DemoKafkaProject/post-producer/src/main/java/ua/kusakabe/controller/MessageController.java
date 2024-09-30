package ua.kusakabe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.kusakabe.dto.PostRR;
import ua.kusakabe.kafka.JsonKafkaProducer;
import ua.kusakabe.payload.Post;

@RestController
@RequestMapping("/post-create")
public class MessageController {

    private JsonKafkaProducer jsonKafkaProducer;

    @Autowired
    public MessageController(JsonKafkaProducer jsonKafkaProducer) {
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

    // --- DELETE BEFORE UPLOAD
    @GetMapping("/test")
    public String test(){
        return "Test request to POST-PRODUCER service!";
    }

    @PostMapping("/new")
    public ResponseEntity<String> publish(@RequestBody PostRR data, @RequestHeader("Authorization") String token){
        jsonKafkaProducer.sendMessage(data, token);
        return ResponseEntity.ok("Message published");
    }

}
