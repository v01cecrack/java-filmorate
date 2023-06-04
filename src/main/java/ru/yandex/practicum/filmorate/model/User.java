package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Valid
@Data
@Builder
public class User {
    private Integer id;
    @Email
    private String email;
    @NotNull
    private String login;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthday;
    private Set<Integer> friends;
}

