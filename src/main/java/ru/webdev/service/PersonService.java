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
