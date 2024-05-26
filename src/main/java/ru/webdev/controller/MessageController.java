package ru.webdev.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @PatchMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message, @PathVariable int id) {
        HttpStatus status = messageRepository.existsById(id)? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<Message>(messageRepository.save(message), status);
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable int id) {
        messageRepository.deleteById(id);
    }


}
