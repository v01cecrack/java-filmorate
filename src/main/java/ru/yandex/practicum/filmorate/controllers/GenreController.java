package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
@AllArgsConstructor
public class GenreController {
    private final FilmService filmService;

    @GetMapping
    public List<Genre> getGenreList() {
        log.info("Получен список жанров");
        return filmService.getGenreList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Genre getGenre(@PathVariable int id) {
        log.info("Получен жанр с id = {}", id);
        return filmService.getGenre(id);
    }
}
