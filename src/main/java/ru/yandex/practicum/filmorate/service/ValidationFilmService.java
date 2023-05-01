package ru.yandex.practicum.filmorate.service;

import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@NoArgsConstructor
public class ValidationFilmService {
    private static final LocalDate date = LocalDate.of(1895, 12, 28);

    public static void validateFilm(Film film) {
        if (film.getName() == null || film.getName().isEmpty()) {
            throw new ValidationException("Name не может быть пустым!");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("MAX длина описания — 200 символов!");
        }
        if (film.getReleaseDate().isBefore(date)) {
            throw new ValidationException("Дата релиза не может быть раньше " + date);
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Продолжительность фильма должна быть положительной!");
        }
    }


}
