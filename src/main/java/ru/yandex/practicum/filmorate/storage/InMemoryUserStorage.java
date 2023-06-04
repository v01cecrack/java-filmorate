package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> userMap = new HashMap<>();
    private static int id;

    @Override
    public User addUser(@Valid User user) {
        user.setId(generateUserId());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(@Valid User user) {
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
            return user;
        }
        log.error("ID введен неверно, такого пользователя не существует");
        throw new ObjectNotFoundException("Такого пользователя не существует");
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findUserById(int id) {
        if (userMap.containsKey(id)) {
            return userMap.get(id);
        }
        log.error("ERROR: ID введен неверно - такого пользователя не существует!");
        throw new ObjectNotFoundException(String.format("Users id %d is not found", id));
    }

    @Override
    public Map<Integer, User> getUsersMap() {
        return null;
    }

    private int generateUserId() {
        return ++id;
    }
}