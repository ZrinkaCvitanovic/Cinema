package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.Dvorana;

import java.util.List;

public interface DvoranaRepository {
    List<Dvorana> getDvorane();
    Dvorana getDvorana(String ime);
    List<Dvorana> filterPoNazivu(String ime);
    List<Dvorana> filterPoDostupnosti(boolean otvorena);
    void save(Dvorana dvorana);
    void deletePoNazivu(String ime);
}
