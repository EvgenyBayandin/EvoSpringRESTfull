package ru.webdev.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String text;
    private LocalDateTime time;

    @ManyToOne
    @JsonIgnore // чтобы приложение не ушло в рекурсию при сериализации вывода в формат JSON
    private Person person; // добавили связь с пользователем


    public Message(String title, String text, LocalDateTime time, Person person) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.person = person;
    }

    public Message(String title, String text) {
        this.title = title;
        this.text = text;
    }
}
