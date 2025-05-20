package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.Redatelj;

import java.util.List;

public interface RedateljRepository {
    List<Redatelj> getRedatelji();
    Redatelj getRedatelj(Integer id);
    void save(Redatelj redatelj);
    void deletePoId(Integer id);
}
