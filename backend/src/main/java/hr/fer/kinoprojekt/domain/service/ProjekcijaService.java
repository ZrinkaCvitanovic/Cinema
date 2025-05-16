package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Projekcija;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.ProjekcijaRepository;
import hr.fer.kinoprojekt.domain.repository.ZaposlenikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjekcijaService {

    private ProjekcijaRepository projekcijaRepository;
    private DvoranaRepository dvoranaRepository;
    private ZaposlenikRepository zaposlenikRepository;
    private FilmRepository filmRepository;

    public List<Projekcija> getProjekcije() {
        return projekcijaRepository.getProjekcije();
    }

    public Projekcija getProjekcijaPoId(String id) {
        return projekcijaRepository.getProjekcija(id);
    }

    public void save(Projekcija projekcija, String imeDvorana, String imeZaposlenik, Integer idFilm) {
        projekcija.setDvorana(dvoranaRepository.getDvorana(imeDvorana));
        projekcija.setUnioZaposlenik(zaposlenikRepository.getPoKorisnickomImenu(imeZaposlenik));
        projekcija.setFilm(filmRepository.getFilm(idFilm));

        projekcijaRepository.save(projekcija);
    }

    public void delete(String id) {
        projekcijaRepository.deletePoId(id);
    }
}
