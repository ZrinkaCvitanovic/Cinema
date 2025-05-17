package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.Projekcija;

import java.util.List;

public interface ProjekcijaRepository {
    List<Projekcija> getProjekcije();
    Projekcija getProjekcija(String id);
    void save(Projekcija projekcija);
    void deletePoId(String id);
    List<Projekcija> filterByDvorana(String ime);
}
