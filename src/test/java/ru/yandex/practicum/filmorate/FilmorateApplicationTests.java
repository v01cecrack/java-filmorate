package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.RatingMpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.UserDbStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {
    private final UserDbStorage userDbStorage;
    private final FilmDbStorage filmDbStorage;
    Film film3 = Film.builder()
            .name("sdsds")
            .description("dadgfgfgffs")
            .releaseDate(LocalDate.of(2000, 12, 12))
            .duration(3)
            .mpa(RatingMpa.builder().id(1).build()).build();
    Film film4 = Film.builder()
            .id(1).name("sdsds")
            .description("dadgfgfgffs")
            .releaseDate(LocalDate.of(2000, 12, 12))
            .duration(3)
            .mpa(RatingMpa.builder().id(1).build()).build();
    User user1 = User.builder().id(1)
            .email("kkk@mail.ru")
            .login("rsrs").name("bobobo")
            .birthday(LocalDate.of(2002,03,12))
            .build();

    @Test
    void addFilmTest() {
        assertEquals(film3, filmDbStorage.addFilm(film3));
    }

    @Test
    void updateFilmTest() {
        assertEquals(film4, filmDbStorage.updateFilm(film4));
    }

    @Test
    void getFilmsTest() {
        assertEquals(1, filmDbStorage.getFilms().size());
    }

    @Test
    void findFilmByIdTest() {
        assertEquals(film4, filmDbStorage.findFilmById(1));
    }

    @Test
    void addUserTest() {
        assertEquals(user1, userDbStorage.addUser(user1));
    }

    @Test
    void updateUserTest(){
        assertEquals(user1,userDbStorage.updateUser(user1));
    }

    @Test
    void getUsersTest(){
        assertEquals(1,userDbStorage.getUsers().size());
    }

    @Test
    void findUserByIdTest(){
        assertEquals(user1,userDbStorage.findUserById(1));
    }
}
