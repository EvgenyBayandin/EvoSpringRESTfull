package ru.webdev;

import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private Person person;

    @PostMapping("/person")
    public Person setPerson(@RequestBody Person person) {
        this.person = person;
        return person;
    }

    @GetMapping("/person")
    public Person getPerson() {
        return new Person(1, "Ivanov", "Ivan", "Ivanovich", LocalDate.of(1991, 12, 12));
    }

    @GetMapping
    public String hello() {
        return "Hello, world!";
    }
}
