package ru.webdev.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.webdev.dto.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

}
