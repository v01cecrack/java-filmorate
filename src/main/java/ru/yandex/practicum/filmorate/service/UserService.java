package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User addUser(User user);

    User updateUser(User user);

    List<User> getUsers();
}
