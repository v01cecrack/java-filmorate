package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserStorage {
    User addUser(@Valid User user);

    User updateUser(@Valid User user);

    List<User> getUsers();

    User findUserById(int id);

}
