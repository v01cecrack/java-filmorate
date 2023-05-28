package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private Integer id;
    @NotBlank()
    @Size(max = 200)
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private RatingMpa mpa;
    private List<Genre> genres;
    private Set<Integer> likes;
}
