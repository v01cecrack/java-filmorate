package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.ValidationFilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@AllArgsConstructor
public class FilmController {
    private FilmService filmService;
    private ValidationFilmService validationFilmService;

    @PostMapping()
    public Film addFilm(@RequestBody Film film) {
        validationFilmService.validateFilm(film);
        filmService.addFilm(film);
        log.info("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        validationFilmService.validateFilm(film);
        filmService.updateFilm(film);
        log.info("Обновление данных по фильму: {}", film);
        return film;
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getFilms() {
        return filmService.getFilms();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Film getFilm(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        filmService.addLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.getTopFilms(count);
    }

}
