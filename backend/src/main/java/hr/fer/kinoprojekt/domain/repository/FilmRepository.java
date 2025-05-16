package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.Film;

import java.util.List;

public interface FilmRepository {
    List<Film> getFilmovi();
    Film getFilm(Integer id);
    void save(Film film);
    void deletePoId(Integer id);
}
