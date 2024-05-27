package ru.webdev.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdev.dto.Message;
import ru.webdev.repository.MessageRepository;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/message")
    public Iterable<Message> getMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/message/{id}")
    public Optional<Message> getMessageById(@PathVariable int id) {
        return messageRepository.findById(id);
    }

    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        messageRepository.save(message);
        return message;
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message, @PathVariable int id) {
        HttpStatus status = messageRepository.existsById(id)? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<Message>(messageRepository.save(message), status);
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable int id) {
        messageRepository.deleteById(id);
    }

}
