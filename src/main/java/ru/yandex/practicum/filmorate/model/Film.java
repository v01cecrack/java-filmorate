package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Film {
    private Integer id;
    @NotNull
    @NotBlank()
    @Size(max = 200)
    private String name;
    @NotNull
    private String description;
    @NotNull
    private LocalDate releaseDate;
    private int duration;
    @NotNull
    private RatingMpa mpa;
    @NotNull
    private List<Genre> genres;
    @NotNull
    private Set<Integer> likes;
}
