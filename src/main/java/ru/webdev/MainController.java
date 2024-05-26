package ru.webdev;

import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private Person person;

    @PostMapping("/person")
    public ResponseEntity<Person> setPerson(@RequestBody Person person) {
        this.person = person;
        return new ResponseEntity<Person>(person, HttpStatus.CREATED);
    }

    @GetMapping("/person")
    public ResponseEntity<Person> getPerson() {
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }

    @GetMapping
    public String hello() {
        return "Hello, world!";
    }
}
