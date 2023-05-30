package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@AllArgsConstructor
public abstract class DbStorage {
    protected final JdbcTemplate jdbcTemplate;
}
