package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Administrator;
import hr.fer.kinoprojekt.domain.repository.ZaposlenikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdministratorService{

    private ZaposlenikRepository administratorRepository;

    public List<Administrator> getAdministrators() {
        return administratorRepository.getAdministrators();
    }
}
