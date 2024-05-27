package ru.webdev.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    // Добавление сообщения Message в объект Person по p_id
    @PostMapping("/person/{p_id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable int p_id, @RequestBody Message message) {
        return service.addMessageToPerson(p_id, message);
    }

    // Удаление сообщения Message с m_id из объекта Person с p_id
    @DeleteMapping("/person/{p_id}/message/{m_id}")
    public ResponseEntity<Person> deleteMessageByIdAndPersonId(@PathVariable int p_id, @PathVariable int m_id) {
        return service.removeMessageByIdFromPersonById(p_id, m_id);
    }

    // Возврат списка сообщений Message для объекта Person по p_id
    @GetMapping("/person/{p_id}/message")
    public ResponseEntity<Iterable <Message>> getMessagesByPersonId(@PathVariable int p_id) {
        return service.getMessages(p_id);
    }

    // Возврат сообщения Message с m_id для объекта Person по p_id
    @GetMapping("/person/{p_id}/message/{m_id}")
    public ResponseEntity<Message> getMessageByIdAndPersonId(@PathVariable int p_id, @PathVariable int m_id) {
        return service.getMessageByIdAndPersonId(p_id, m_id);
    }

    // Обновление объекта Person по id
//    @PatchMapping("/person/{id}")
//    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable int id) {
//       HttpStatus status = personRepository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
//       return new ResponseEntity<Person>(personRepository.save(person), status);
//    }

    // Изменение объекта Person по id
    @PutMapping("/person/{id}")
    public ResponseEntity<Person> updatePersonById(@RequestBody Person person, @PathVariable int id) {
        HttpStatus status = personRepository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<Person>(personRepository.save(person), status);
    }

    // Возврат списка объектов Person
    @GetMapping("/person")
    public Iterable<Person> getPerson() {
        return personRepository.findAll();
    }

    // Возврат объекта Person по id
    @GetMapping("/person/{id}")
    public Optional<Person> getPersonById(@PathVariable int id) {
        return personRepository.findById(id);
    }

    // Удаление объекта Person по id
    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable int id) {
        personRepository.deleteById(id);
    }

    @GetMapping
    public String hello() {
        return "Hello, world!";
    }
}
