package ru.webdev;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private Person person;

    @GetMapping("/person")
    public Person getPerson() {
        return new Person(1, "Ivanov", "Ivan", "Ivanovich", LocalDate.of(1991, 12, 12));
    }

    @GetMapping
    public String hello() {
        return "Hello, world!";
    }
}
