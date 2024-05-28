package ru.webdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.webdev.dto.Message;
import ru.webdev.repository.MessageRepository;
import ru.webdev.service.MessageService;

@RestController
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService service;

    // Возврат списка объектов Message
    @GetMapping("/message")
    public ResponseEntity<Iterable<Message>> getMessages() {
        return service.getMessages();
    }

    // Возврат объекта Message по id
    @GetMapping("/message/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        return service.getMessage(id);
    }

    // Добавление объекта Message
    @PostMapping("/message")
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        return service.addMessage(message);
    }

    // Изменение объекта Message по id
    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@RequestBody Message message, @PathVariable int id) {
        return service.updateMessage(id, message);
    }

    // Удаление объекта Message по id
    @DeleteMapping("/message/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable int id) {
        return service.deleteMessage(id);
    }

}
