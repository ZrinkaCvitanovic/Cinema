package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.repository.RedateljRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RedateljService {

    private RedateljRepository repository;

    public List<Redatelj> getRedatelji() {
        return repository.getRedatelji();
    }

    public Redatelj getRedatelj(Integer id) {
        return repository.getRedatelj(id);
    }

    public void save(Redatelj redatelj) {
        repository.save(redatelj);
    }

    public void delete(Integer id) {
        repository.deletePoId(id);
    }
}
