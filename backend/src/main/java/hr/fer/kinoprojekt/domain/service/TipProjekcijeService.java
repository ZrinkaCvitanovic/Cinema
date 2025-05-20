package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.repository.TipProjekcijeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TipProjekcijeService {

    private TipProjekcijeRepository repository;

    public List<TipProjekcije> getTipoviProjekcije() {
        return repository.getTipoviProjekcije();
    }

    public TipProjekcije getTipProjekcije(Integer id) {
        return repository.getTipProjekcije(id);
    }

    public void save(TipProjekcije tip) {
        repository.save(tip);
    }

    public void delete(Integer id) {
        repository.deletePoId(id);
    }
}
