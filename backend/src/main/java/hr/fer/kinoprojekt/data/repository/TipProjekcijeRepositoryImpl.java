package hr.fer.kinoprojekt.data.repository;

import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.repository.TipProjekcijeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TipProjekcijeRepositoryImpl implements TipProjekcijeRepository {

    private JpaTipProjekcijaRepository repository;

    @Override
    public List<TipProjekcije> getTipoviProjekcije() {
        return repository.findAll();
    }

    @Override
    public TipProjekcije getTipProjekcije(Integer id) {
        return repository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    @Override
    public void save(TipProjekcije tipProjekcije) {
        repository.save(tipProjekcije);
    }

    @Override
    public void deletePoId(Integer id) {
        repository.deleteById(id);
    }
}

@Repository
interface JpaTipProjekcijaRepository extends JpaRepository<TipProjekcije, Integer> {
}
