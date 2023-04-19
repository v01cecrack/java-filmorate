package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.impl.ImplFilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {
    private ImplFilmService filmService;

    @PostMapping()
    public Film addFilm(@RequestBody Film film) {
        filmService.addFilm(film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmService.updateFilm(film);
        log.info("Обновление данных по фильму: {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilms();
    }
}
