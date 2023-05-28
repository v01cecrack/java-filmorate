package ru.yandex.practicum.filmorate.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;


@NoArgsConstructor
@Service
public class ValidationUserService {
    public void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ValidationException("Email не может быть пустым!");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Email должно содержать символ @");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty()) {
            throw new ValidationException("Login не может быть пустым!");
        }
        if (user.getLogin().contains(" ")) {
            throw new ValidationException("Login не может содержать пробелы!");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday не может быть в будущем!");
        }
        if (user.getName() == null || user.getName().isEmpty() || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
