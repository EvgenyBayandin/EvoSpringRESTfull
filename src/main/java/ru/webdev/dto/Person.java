package ru.webdev.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private int id;
    private String firstname;
    private String surname;
    private String lastname;
    private String birthday;

    @OneToMany(cascade = CascadeType.ALL) // для касскадного удаления сообщений пользователя при его удалении
    List<Message> messages; // добавили обратную связь с сообщениями

    public Person(String firstname, String surname, String lastname, String birthday, List<Message> messages) {
        this.firstname = firstname;
        this.surname = surname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.messages = messages;
    }

    // создаем метод добааления сообщения
    public void addMessage(Message message) {
        messages.add(message);
    }
}
