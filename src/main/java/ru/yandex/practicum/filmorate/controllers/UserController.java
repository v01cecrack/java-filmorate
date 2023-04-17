package ru.yandex.practicum.filmorate.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.impl.ImplUserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private ImplUserService userService;
    @PostMapping
    public User addUser(@RequestBody User user){
        userService.addUser(user);
        log.info("Добавлен пользователь: {}", user);
        return user;
    }
    @PutMapping
    public User updateUser(@RequestBody User user){
        userService.updateUser(user);
        log.info("Обновление данных пользователя: {}", user);
        return user;
    }
    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }
}
