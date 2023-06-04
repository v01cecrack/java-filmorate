package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static int id;
    private final Map<Integer, Film> filmMap = new HashMap<>();

    @Override
    public Film addFilm(Film film) {
        film.setId(generateFilmId());
        filmMap.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmMap.containsKey(film.getId())) {
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

    @Override
    public Film findFilmById(int filmId) {
        if (!filmMap.containsKey(filmId)) {
            throw new ObjectNotFoundException(String.format("Film id %d is not found", filmId));
        }
        return filmMap.get(filmId);
    }

    private int generateFilmId() {
        return ++id;
    }
}