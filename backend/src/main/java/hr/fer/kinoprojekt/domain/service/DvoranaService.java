package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Dvorana;
import hr.fer.kinoprojekt.domain.model.Zaposlenik;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import hr.fer.kinoprojekt.domain.repository.ZaposlenikRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DvoranaService{

    private DvoranaRepository dvoranaRepository;
    private ZaposlenikRepository zaposlenikRepository;

    public List<Dvorana> getDvorane() {
        return dvoranaRepository.getDvorane();
    }

    public Dvorana getDvorana(String ime) {
        return dvoranaRepository.getDvorana(ime);
    }

    public void save(Dvorana dvorana, String zaposlenikKorisnickoIme) {
        final Zaposlenik zaposlenik = zaposlenikRepository.getPoKorisnickomImenu(zaposlenikKorisnickoIme);
        dvorana.setZaposlenik(zaposlenik);
        dvoranaRepository.save(dvorana);
    }

    public void delete(String ime) {
        dvoranaRepository.deletePoNazivu(ime);
    }
}
