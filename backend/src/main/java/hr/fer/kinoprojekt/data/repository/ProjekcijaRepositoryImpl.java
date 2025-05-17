package hr.fer.kinoprojekt.data.repository;

import hr.fer.kinoprojekt.domain.model.Projekcija;
import hr.fer.kinoprojekt.domain.repository.ProjekcijaRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProjekcijaRepositoryImpl implements ProjekcijaRepository {

    private JpaProjekcijaRepository repository;

    @Override
    public List<Projekcija> getProjekcije() {
        return repository.findAll();
    }

    @Override
    public Projekcija getProjekcija(String id) {
        return repository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    @Override
    public void save(Projekcija projekcija) {
        repository.save(projekcija);
    }

    @Override
    public void deletePoId(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<Projekcija> filterByDvorana(String ime) {
       return repository.getProjekcijaByDvorana_Ime(ime);
    }
}

@Repository
interface JpaProjekcijaRepository extends JpaRepository<Projekcija, String> {
    List<Projekcija> getProjekcijaByDvorana_Ime(String dvoranaIme);
}
