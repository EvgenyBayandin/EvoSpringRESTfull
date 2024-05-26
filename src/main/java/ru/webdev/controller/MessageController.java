package ru.webdev.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.webdev.dto.Message;

@RestController
public class MessageController {

    List<Message> messages = new ArrayList<>(java.util.Arrays.asList(
            new Message(1, "Hello", "Hello World!", LocalDateTime.of(2024, 05, 26, 12, 00, 00)),
            new Message(2, "Hi", "Hi Java!", LocalDateTime.of(2024, 05, 26, 12, 10, 00)),
            new Message(3, "Bye", "Bye Java!", LocalDateTime.of(2024, 05, 26, 12, 20, 00)),
            new Message(4, "Hello", "Hello World!", LocalDateTime.now())
    ));

    @GetMapping("/message")
    public Iterable<Message> getMessages() {
        return messages;
    }

}
