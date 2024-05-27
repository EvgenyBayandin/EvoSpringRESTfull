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

    // Добавление сообщения Message в объект Person с p_id
    @PostMapping("/person/{p_id}/message")
    public ResponseEntity<Person> addMessage(@PathVariable int p_id, @RequestBody Message message) {
        if (!personRepository.existsById(p_id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Person person = personRepository.findById(p_id).get();
            person.addMessage(message);
            message.setPerson(person);
            message.setTime(LocalDateTime.now());
            personRepository.save(person);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
    }

    // Удаление сообщения Message с m_id из объекта Person с p_id
    @DeleteMapping("/person/{p_id}/message/{m_id}")
    public ResponseEntity<Void> deleteMessageByIdAndPersonId(@PathVariable int p_id, @PathVariable int m_id) {
        // Проверяем, существует ли пользователь с таким ID
        Optional<Person> optionalPerson = personRepository.findById(p_id);
        if (!optionalPerson.isPresent()) {
            // Возвращаем код 404, если пользователь не найден
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Person person = optionalPerson.get();
        // Проверяем, принадлежит ли сообщение пользователю
        boolean belongsToUser = person.getMessages().stream()
                .anyMatch(m -> m.getId() == m_id);
        if (!belongsToUser) {
            // Возвращаем код 404, если сообщение не принадлежит пользователю
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Удаляем сообщение из списка сообщений пользователя
        person.getMessages().removeIf(m -> m.getId() == m_id);
        // Сохраняем изменения
        personRepository.save(person);
        // Возвращаем код 200, если сообщение успешно удалено
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Возврат списка сообщений Message для объекта Person по p_id
    @GetMapping("/person/{p_id}/message")
    public List<Message> getMessages(@PathVariable int p_id) {
        return personRepository.findById(p_id).get().getMessages();
    }

    // Возврат сообщения Message с m_id для объекта Person по p_id
    @GetMapping("/person/{p_id}/message/{m_id}")
    public Message getMessageByIdAndPersonId(@PathVariable int p_id, @PathVariable int m_id) {
        return personRepository.findById(p_id).get().getMessages().stream()
                .filter(m -> m.getId() == m_id)
                .findFirst()
                .orElse(null);
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
