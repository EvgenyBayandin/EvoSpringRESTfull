package ru.webdev.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private int id;
    private String firstname;
    private String surname;
    private String lastname;
    private LocalDate birthday;

}
