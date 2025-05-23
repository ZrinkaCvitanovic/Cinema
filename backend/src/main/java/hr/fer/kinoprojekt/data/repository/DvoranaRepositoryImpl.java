package hr.fer.kinoprojekt.data.repository;

import hr.fer.kinoprojekt.domain.model.Dvorana;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class DvoranaRepositoryImpl implements DvoranaRepository {

    private JpaDvoranaRepository repository;

    @Override
    public List<Dvorana> getDvorane() {
        return repository.findAll();
    }

    @Override
    public Dvorana getDvorana(String ime) {
        return repository.findById(ime).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Dvorana> filterPoNazivu(String ime) {
        return repository.findByImeStartingWith(ime);
    }

    @Override
    public List<Dvorana> filterPoDostupnosti(boolean otvorena) {
        return repository.findByOtvorena(otvorena);
    }

    @Override
    public void save(Dvorana dvorana) {
        repository.save(dvorana);
    }

    @Modifying
    @Transactional
    @Override
    public void deletePoNazivu(String ime) {
        repository.deleteByIme(ime);
    }
}

@Repository
interface JpaDvoranaRepository extends JpaRepository<Dvorana, String> {
    List<Dvorana> findByImeStartingWith(String ime);

    List<Dvorana> findByOtvorena(boolean otvorena);

    void deleteByIme(String ime);
}
