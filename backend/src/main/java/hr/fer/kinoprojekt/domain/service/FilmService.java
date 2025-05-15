package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Zaposlenik;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.ZaposlenikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FilmService {

    private FilmRepository filmRepository;
    private ZaposlenikRepository zaposlenikRepository;

    public List<Film> getFilmovi() {
        return filmRepository.getFilmovi();
    }

    public Film getFilm(String id) {
        return filmRepository.getFilm(id);
    }

    public void save(Film film, String zaposlenikKorisnickoIme) {
        final Zaposlenik zaposlenik = zaposlenikRepository.getPoKorisnickomImenu(zaposlenikKorisnickoIme);
        film.setZaposlenik(zaposlenik);
        filmRepository.save(film);
    }

    public void delete(String id) {
        filmRepository.deletePoId(id);
    }
}
