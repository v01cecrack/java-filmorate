package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("DbStorage")
@AllArgsConstructor
public abstract class DbStorage {
    protected final JdbcTemplate jdbcTemplate;
}
