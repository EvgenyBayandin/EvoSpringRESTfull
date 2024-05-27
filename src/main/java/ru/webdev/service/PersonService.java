package ru.webdev.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.webdev.dto.Message;
import ru.webdev.dto.Person;
import ru.webdev.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    // Добавление сообщения Message в объект Person по p_id
    public ResponseEntity<Person> addMessageToPerson(int p_id, Message message) {
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
}
