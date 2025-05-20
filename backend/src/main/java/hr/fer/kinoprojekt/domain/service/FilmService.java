package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.RedateljRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {

    private FilmRepository filmRepository;
    private RedateljRepository redateljRepository;

    public List<Film> getFilmovi() {
        return filmRepository.getFilmovi();
    }

    public Film getFilm(Integer id) {
        return filmRepository.getFilm(id);
    }

    public void save(Film film, Integer idRedatelj) {
        Redatelj redatelj = redateljRepository.getRedatelj(idRedatelj);
        film.setRedatelj(redatelj);
        filmRepository.save(film);
    }

    public void delete(Integer id) {
        filmRepository.deletePoId(id);
    }
}
