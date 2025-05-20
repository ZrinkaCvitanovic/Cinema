package hr.fer.kinoprojekt.data.repository;

import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.repository.RedateljRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RedateljRepositoryImpl implements RedateljRepository {

    private JpaRedateljRepository repository;

    @Override
    public List<Redatelj> getRedatelji() {
        return repository.findAll();
    }

    @Override
    public Redatelj getRedatelj(Integer id) {
        return repository.findById(id).orElseThrow(NoSuchFieldError::new);
    }

    @Override
    public void save(Redatelj redatelj) {
        repository.save(redatelj);
    }

    @Override
    public void deletePoId(Integer id) {
        repository.deleteById(id);
    }
}

@Repository
interface JpaRedateljRepository extends JpaRepository<Redatelj, Integer> {

}
