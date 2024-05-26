package ru.webdev.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.webdev.dto.Message;

@RestController
public class MessageController {

    List<Message> messages = new ArrayList<>(Arrays.asList(
            new Message(1, "Hello", "Hello World!", LocalDateTime.of(2024, 05, 26, 12, 00, 00)),
            new Message(2, "Hi", "Hi Java!", LocalDateTime.of(2024, 05, 26, 12, 10, 00)),
            new Message(3, "Bye", "Bye Java!", LocalDateTime.of(2024, 05, 26, 12, 20, 00)),
            new Message(4, "Hello", "Hello World!", LocalDateTime.now())
    ));

    @GetMapping("/message")
    public Iterable<Message> getMessages() {
        return messages;
    }

    @GetMapping("/message/{id}")
    public Optional<Message> getMessageById(@PathVariable int id) {
        return messages.stream().filter(message -> message.getId() == id).findFirst();
    }

    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }



}
