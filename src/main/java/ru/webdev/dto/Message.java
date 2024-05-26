package ru.webdev.dto;

import java.time.LocalDateTime;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class Message {
    private int id;
    private String title;
    private String text;
    private LocalDateTime time;
}
