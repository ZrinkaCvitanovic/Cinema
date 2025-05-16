package hr.fer.kinoprojekt.data.repository;

import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class FilmRepositoryImpl implements FilmRepository {

    private JpaFilmRepository repository;

    @Override
    public List<Film> getFilmovi() {
        return repository.findAll();
    }

    @Override
    public Film getFilm(Integer id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public void save(Film film) {
        repository.save(film);
    }

    @Override
    public void deletePoId(Integer id) {
        repository.deleteById(id);
    }
}

@Repository
interface JpaFilmRepository extends JpaRepository<Film, Integer> {}
