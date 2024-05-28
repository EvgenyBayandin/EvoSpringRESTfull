package ru.webdev.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.webdev.dto.Message;
import ru.webdev.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    // Возврат списка объектов Message
    public ResponseEntity<Iterable<Message>> getMessages() {
        Iterable<Message> messages = messageRepository.findAll();
        if (messages.iterator().hasNext()) {
            return new ResponseEntity<>(messages, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Возврат объекта Message по id
    public ResponseEntity<Message> getMessage(int id) {
        Optional<Message> m = messageRepository.findById(id);
        if (m.isPresent()) {
            return new ResponseEntity<>(m.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    // Добавление объекта Message
    public ResponseEntity<Message> addMessage (Message message){
        message.setTime(LocalDateTime.now());
        Message savedMessage = messageRepository.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    // Обновление объекта Message по id
    public ResponseEntity<Message> patchMessage(int id, Message message) {
        Optional<Message> existingMessageOptional = messageRepository.findById(id);
        if (!existingMessageOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Message existingMessage = existingMessageOptional.get();
        if(message.getTitle() != null){
            existingMessage.setTitle(message.getTitle());
        }
        if(message.getText() != null){
            existingMessage.setText(message.getText());
        }
        if(message.getTime()!= null){
            existingMessage.setTime(message.getTime());
        } else {
            message.setTime(existingMessage.getTime());
        }
        Message updatedMessage = messageRepository.save(existingMessage);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
    }

    // Изменение объекта Message по id
    public ResponseEntity<Message> putMessage(int id, Message message) {
        Optional<Message> existingMessageOptional = messageRepository.findById(id);
        if (!existingMessageOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Message existingMessage = existingMessageOptional.get();
        existingMessage.setTitle(message.getTitle());
        existingMessage.setText(message.getText());
        existingMessage.setTime(message.getTime());
        Message updatedMessage = messageRepository.save(existingMessage);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
    }

    // Удаление объекта Message по id
    public ResponseEntity<Message> deleteMessage(int id) {
        HttpStatus status = messageRepository.existsById(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        messageRepository.deleteById(id);
        return new ResponseEntity<>(status);
    }


}


