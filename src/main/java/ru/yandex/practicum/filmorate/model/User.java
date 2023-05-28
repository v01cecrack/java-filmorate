package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    private Integer id;
    @Email
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friends;
}

