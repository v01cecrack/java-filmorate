package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Qualifier("FilmDbStorage")
    private final FilmStorage filmStorage;
    private final RatingMpaStorage ratingMpaStorage;
    private final GenreStorage genreStorage;
    private final LikesStorage likesStorage;
    private final FilmGenreStorage filmGenreStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, RatingMpaStorage ratingMpaStorage,
                       GenreStorage genreStorage, LikesStorage likesStorage,
                       FilmGenreStorage filmGenreStorage) {
        this.filmStorage = filmStorage;
        this.ratingMpaStorage = ratingMpaStorage;
        this.genreStorage = genreStorage;
        this.likesStorage = likesStorage;
        this.filmGenreStorage = filmGenreStorage;
    }

    public Film addFilm(Film film) {
        filmStorage.addFilm(film);
        addGenreForFilm(film);
        return film;
    }

    private void addGenreForFilm(Film film) {
        if (film.getGenres() != null && film.getGenres().size() > 0) {

            List<Genre> ratingList = film.getGenres();

            Set<Integer> set = new HashSet<>();

            ratingList.removeIf(rating -> !set.add(rating.getId()));
            film.setGenres(ratingList);

            for (var genre : film.getGenres()) {
                var filmGenre = new FilmGenre(film.getId(), genre.getId());
                filmGenreStorage.add(filmGenre);
            }
        }
    }


    public void addLike(int userId, int filmId) {
        Likes like = likesStorage.getLikesUserFilmId(userId, filmId);
        if (like == null) {
            likesStorage.add(new Likes(filmId, userId));
        }
    }

    public void deleteLike(int userId, int filmId) {
        Likes like = likesStorage.getLikesUserFilmId(userId, filmId);
        if (like == null) {
            throw new ObjectNotFoundException("doesn't found!");
        }
        likesStorage.delete(new Likes(filmId, userId));
    }

    public List<Film> getTopFilms(int count) {
        List<Film> films = getFilms();
        return films.stream()
                .sorted(Comparator.comparingInt((Film film) -> film.getLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film findFilmById(int filmId) {
        var film = filmStorage.findFilmById(filmId);

        var mpaList = ratingMpaStorage.findAllRating();
        var genres = genreStorage.findAllGenres();
        var filmGenres = filmGenreStorage.getLikesFilmId(film.getId());
        var likes = likesStorage.getLikesFilmId(film.getId());

        setMpaGenreLikesForFilm(film, mpaList, genres, filmGenres, likes);
        return film;
    }

    public Film updateFilm(Film film) {
        try {
            filmStorage.updateFilm(film);
        } catch (Exception e) {
            throw new ObjectNotFoundException("Такого фильма не существует!");
        }
        filmGenreStorage.deleteByFilmId(film.getId());
        addGenreForFilm(film);
        return film;
    }

    public List<Film> getFilms() {
        var films = filmStorage.getFilms();
        var mpaList = ratingMpaStorage.findAllRating();
        var genres = genreStorage.findAllGenres();
        var filmGenres = filmGenreStorage.findAllFilmGenre();
        var likes = likesStorage.findAllLikes();
        for (var film : films) {
            setMpaGenreLikesForFilm(film, mpaList, genres, filmGenres, likes);
        }
        return films;
    }

    public Genre getGenre(int id) {
        return genreStorage.findGenreById(id);
    }

    public List<Genre> getGenreList() {
        return genreStorage.findAllGenres();
    }


    public RatingMpa getMpa(int id) {
        return ratingMpaStorage.findRatingById(id);
    }


    public List<RatingMpa> getMpaList() {
        var mpaList = ratingMpaStorage.findAllRating();
        List<RatingMpa> list = new ArrayList<>(mpaList);
        Collections.sort(list, Comparator.comparing(RatingMpa::getId));
        return list;
    }

    private void setMpaGenreLikesForFilm(Film film, Set<RatingMpa> mpaList, List<Genre> genres,
                                         List<FilmGenre> filmGenres, Set<Likes> likes) {
        List<Genre> genreByFilm = new ArrayList<>();
        filmGenres.stream().filter(f -> f.getFilmId() == film.getId())
                .forEach(f -> genreByFilm.add(
                        new Genre(f.getGenreId(),
                                genres.stream().filter(g -> g.getId() == f.getGenreId()).findAny().get().getName())));

        film.setGenres(genreByFilm);

        film.getMpa().setName(mpaList.stream().filter(m -> m.getId() == film.getMpa().getId()).findAny().get().getName());

        Set<Integer> likesByFilm = new HashSet<>();
        likes.stream().filter(l -> l.getFilmId() == film.getId()).forEach(l -> likesByFilm.add(l.getUserId()));
        film.setLikes(likesByFilm);

    }
}
