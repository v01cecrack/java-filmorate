package ru.yandex.practicum.filmorate.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    final String error;
    final String description;
}

