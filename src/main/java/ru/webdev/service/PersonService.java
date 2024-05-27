package ru.webdev.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.webdev.dto.Message;
import ru.webdev.dto.Person;
import ru.webdev.repository.MessageRepository;
import ru.webdev.repository.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MessageRepository messageRepository;


    // Добавление объекта Person
    public ResponseEntity<Person> addPerson(Person person) {
        Person savedPerson = personRepository.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    // Изменение объекта Person по id
    public ResponseEntity<Person> updatePerson(Person person, int id) {
        HttpStatus status = personRepository.existsById(id) ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<Person>(personRepository.save(person), status);
    }

    // Удаление объекта Person по id
    public ResponseEntity<Person> deletePerson(int id) {
        HttpStatus status = personRepository.existsById(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        personRepository.deleteById(id);
        return new ResponseEntity<>(status);
    }


    // Возврат списка объектов Person
    public ResponseEntity<Iterable<Person>> getAllPersons() {
        Iterable<Person> persons = personRepository.findAll();
        if (persons.iterator().hasNext()) {
            return new ResponseEntity<>(persons, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Возврат списка сообщений Message для объекта Person по p_id
    public ResponseEntity<Iterable<Message>> getMessages(int p_id) {
        Optional<Person> person = personRepository.findById(p_id);
        if (person.isPresent()) {
            return new ResponseEntity<>(messageRepository.findByPersonId(p_id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Возврат объекта Person по id
    public ResponseEntity<Person> getPerson(int p_id) {
        Optional<Person> person = personRepository.findById(p_id);
        if (person.isPresent()) {
            return new ResponseEntity<>(person.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Возврат сообщения Message с m_id для объекта Person по p_id
    public ResponseEntity<Message> getMessageByIdAndPersonId(int p_id, int m_id) {
        Optional<Person> person = personRepository.findById(p_id);
        if (person.isPresent()) {
            Optional<Message> message = messageRepository.findById(m_id);
            if (message.isPresent()) {
                return new ResponseEntity<>(message.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


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

    // Удаление сообщения Message с m_id из объекта Person с p_id
    public ResponseEntity<Person> removeMessageByIdFromPersonById(int p_id, int m_id) {
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
        messageRepository.findById(m_id).ifPresent(messageRepository::delete);
        // Сохраняем изменения
        personRepository.save(person);
        // Возвращаем код 200, если сообщение успешно удалено
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
