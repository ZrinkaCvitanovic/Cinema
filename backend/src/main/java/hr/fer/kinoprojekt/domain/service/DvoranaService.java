package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Dvorana;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DvoranaService {
    private final DvoranaRepository dvoranaRepository;

    public DvoranaService(DvoranaRepository dvoranaRepository) {
        this.dvoranaRepository = dvoranaRepository;
    }

    public List<Dvorana> getDvorane() {
        return dvoranaRepository.getDvorane();
    }

    public Dvorana getDvorana(String ime) {
        return dvoranaRepository.getDvorana(ime);
    }
}
