package hr.fer.kinoprojekt.domain.repository;

import hr.fer.kinoprojekt.domain.model.Administrator;
import hr.fer.kinoprojekt.domain.model.Blagajnik;
import hr.fer.kinoprojekt.domain.model.Zaposlenik;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ZaposlenikRepository {
    Zaposlenik getPoKorisnickomImenu(String korisnickoIme);
    void saveBlagajnik(Blagajnik blagajnik);
    void saveAdministrator(Administrator administrator);
    void detetePoId(String id);

    List<Administrator> getAdministrators();
}
