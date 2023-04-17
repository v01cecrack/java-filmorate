package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.service.impl.ValidationService.validateUser;

@Slf4j
@Service
public class ImplUserService implements UserService {
    private final Map<Integer, User> userMap = new HashMap<>();
    private static int id;

    @Override
    public User addUser(User user) {
        validateUser(user);
        user.setId(generateUserId());
        userMap.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (userMap.containsKey(user.getId())) {
            validateUser(user);
            userMap.remove(user.getId());
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

    private int generateUserId() {
        return ++id;
    }
}
