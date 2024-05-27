package ru.webdev.controller;

import java.time.LocalDate;
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
import ru.webdev.dto.Person;
import ru.webdev.repository.PersonRepository;
import ru.webdev.service.PersonService;

@RestController
public class PersonController {
    /*
    POST <http://localhost:8080/person>
    Content-Type: application/json

    {
      "firstname": "Василий",
      "surname": "Викторович",
      "lastname": "Петрушин",
      "birthday": "2001-01-01"
    }
    */

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService service;

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        personRepository.save(person);
        return person;
    }

    // добавление сообщения конкретному пользователю
    @PostMapping("/person/{id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable int id, @RequestBody Message message) {
        if (!personRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Person person = personRepository.findById(id).get();
            person.addMessage(message);
            personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }

    @PatchMapping("/person/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable int id) {
       HttpStatus status = personRepository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
       return new ResponseEntity<Person>(personRepository.save(person), status);
    }

    @GetMapping("/person")
    public Iterable<Person> getPerson() {
        return personRepository.findAll();
    }

    @GetMapping("/person/{id}")
    public Optional<Person> getPersonById(@PathVariable int id) {
        return personRepository.findById(id);
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable int id) {
        personRepository.deleteById(id);
    }

    @GetMapping
    public String hello() {
        return "Hello, world!";
    }
}
