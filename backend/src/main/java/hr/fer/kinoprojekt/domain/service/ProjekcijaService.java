package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.application.dto.ProjekcijaDto;
import hr.fer.kinoprojekt.application.dto.SpremiProjekcijeDto;
import hr.fer.kinoprojekt.domain.model.Projekcija;
import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.ProjekcijaRepository;
import hr.fer.kinoprojekt.domain.repository.TipProjekcijeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProjekcijaService {

    private ProjekcijaRepository projekcijaRepository;
    private DvoranaRepository dvoranaRepository;
    private FilmRepository filmRepository;
    private TipProjekcijeRepository tipProjekcijeRepository;

    public List<Projekcija> getProjekcije() {
        return projekcijaRepository.getProjekcije();
    }

    public Projekcija getProjekcijaPoId(String id) {
        return projekcijaRepository.getProjekcija(id);
    }

    public void save(Projekcija projekcija, String imeDvorana, Integer idFilm, Integer idTip) {
        projekcija.setDvorana(dvoranaRepository.getDvorana(imeDvorana));
        projekcija.setFilm(filmRepository.getFilm(idFilm));
        TipProjekcije tip = tipProjekcijeRepository.getTipProjekcije(idTip);
        projekcija.setTip(tip);
        projekcijaRepository.save(projekcija);
    }

    public void delete(String id) {
        projekcijaRepository.deletePoId(id);
    }

    public List<Projekcija> filterByDvorana(String ime) {
        return projekcijaRepository.filterByDvorana(ime);
    }

    public List<Projekcija> filterByFilm (Integer ime) {
        return projekcijaRepository.filterByFilm(ime);
    }

    public boolean checkAvailability(SpremiProjekcijeDto projekcija) {
        String datum = projekcija.getDatum();
        String vrijeme = projekcija.getVrijemePoc();
        List<Projekcija> filteredByFilm = projekcijaRepository.filterByFilm(projekcija.getIdFilm());
        List<Projekcija> filteredByDatum = filteredByFilm.stream().filter((p -> p.getDatum().toString().equals(datum) )).toList();
        List<String> vremena = filteredByDatum.stream().map(p -> p.getVrijemePoc().toString()).toList();
        return (!vremena.contains(vrijeme));

        //dohvatiti sve projekcije filma preko filterByFilm
        //spremiti sva vremena u temp varijablu
        //ako trenutno vrijeme nije u tome, prihvaćam
        //inače odbijem i objasnim error
    }

}
