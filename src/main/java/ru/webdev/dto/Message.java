package ru.webdev.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    public Message(String title, String text, LocalDateTime time) {
        this.title = title;
        this.text = text;
        this.time = time;
    }
}
