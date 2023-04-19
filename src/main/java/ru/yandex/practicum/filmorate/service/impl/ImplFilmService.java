package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.service.impl.ValidationFilmService.validateFilm;

@Service
@Slf4j
public class ImplFilmService implements FilmService {
    private final Map<Integer, Film> filmMap = new HashMap<>();
    private static int id;

    @Override
    public Film addFilm(Film film) {
        validateFilm(film);
        film.setId(generateFilmId());
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmMap.containsKey(film.getId())) {
            validateFilm(film);
            filmMap.put(film.getId(), film);
            return film;
        }
        log.error("ID введен неверно, такого фильма не существует");
        throw new ObjectNotFoundException("Такого фильма не существует");
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(filmMap.values());
    }

    private int generateFilmId() {
        return ++id;
    }
}
