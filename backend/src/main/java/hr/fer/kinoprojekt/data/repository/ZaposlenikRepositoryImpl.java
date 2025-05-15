package hr.fer.kinoprojekt.data.repository;

import hr.fer.kinoprojekt.domain.model.Administrator;
import hr.fer.kinoprojekt.domain.model.Blagajnik;
import hr.fer.kinoprojekt.domain.model.Zaposlenik;
import hr.fer.kinoprojekt.domain.repository.ZaposlenikRepository;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ZaposlenikRepositoryImpl implements ZaposlenikRepository {
    private AdministratorRepository administratorRepository;
    private BlagajnikRepository blagajnikRepository;

    @Override
    public Zaposlenik getPoKorisnickomImenu(String korisnickoIme) {
        final Administrator administrator = administratorRepository.findByKorisnickoIme(korisnickoIme);
        final Blagajnik blagajnik = blagajnikRepository.findByKorisnickoIme(korisnickoIme);

        if (blagajnik != null) {
            return blagajnik;
        } else if (administrator != null) {
            return administrator;
        } else {
            throw new NoResultException();
        }
    }

    @Override
    public void saveBlagajnik(Blagajnik blagajnik) {
        blagajnikRepository.save(blagajnik);
    }

    @Override
    public void saveAdministrator(Administrator administrator) {
        administratorRepository.save(administrator);
    }

    @Override
    public void detetePoId(final String id) {
        administratorRepository.deleteByIdAdmin(id);
        blagajnikRepository.deleteByIdBlagajnik(id);
    }
}

@Repository
interface AdministratorRepository extends JpaRepository<Administrator, String> {
    void deleteByIdAdmin(String idAdmin);
    Administrator findByKorisnickoIme(String korisnickoIme);
}

@Repository
interface BlagajnikRepository extends JpaRepository<Blagajnik, String> {
    void deleteByIdBlagajnik(String id);
    Blagajnik findByKorisnickoIme(String korisnickoIme);
}
