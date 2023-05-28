package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.ValidationUserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;
    private ValidationUserService validationUserService;

    @PostMapping
    public User addUser(@RequestBody User user) {
        validationUserService.validateUser(user);
        userService.addUser(user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        validationUserService.validateUser(user);
        userService.updateUser(user);
        log.info("Обновление данных пользователя: {}", user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getListOfFriend(@PathVariable int id) {
        return userService.getListOfFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.getMutualFriends(id, otherId);
    }


}
