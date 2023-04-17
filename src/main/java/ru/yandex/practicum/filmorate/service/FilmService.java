package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface FilmService {
     Film addFilm(Film film);
     Film updateFilm(Film film);
     List<Film> getFilms();
}
