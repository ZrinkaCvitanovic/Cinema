package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.TipProjekcije;

import java.util.List;

public interface TipProjekcijeRepository {
    List<TipProjekcije> getTipoviProjekcije();
    TipProjekcije getTipProjekcije(Integer id);
    void save(TipProjekcije tip);
    void deletePoId(Integer id);
}
