package hr.fer.kinoprojekt.config;

import hr.fer.kinoprojekt.domain.model.*;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.ProjekcijaRepository;
import hr.fer.kinoprojekt.domain.repository.ZaposlenikRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Time;
import java.time.LocalDate;

@Configuration
public class DataInitialization {
    @Bean
    public CommandLineRunner initData(
            ZaposlenikRepository zaposlenikRepository,
            FilmRepository filmRepository,
            ProjekcijaRepository projekcijaRepository,
            DvoranaRepository dvoranaRepository
    ) {
        return args -> {
            Administrator admin1 = Administrator.builder()
                    .korisnickoIme("admin1")
                    .lozinka("password1")
                    .salt("salt1")
                    .imeZaposlenika("Ivan")
                    .prezimeZaposlenika("Ivić")
                    .uloga(Uloga.ADMIN)
                    .idAdmin("admin01")
                    .radniSatiMjesec(22)
                    .build();

            Administrator admin2 = Administrator.builder()
                    .korisnickoIme("admin2")
                    .lozinka("password2")
                    .salt("salt2")
                    .imeZaposlenika("Marko")
                    .prezimeZaposlenika("Marić")
                    .uloga(Uloga.ADMIN)
                    .idAdmin("admin02")
                    .radniSatiMjesec(38)
                    .build();

            zaposlenikRepository.saveAdministrator(admin1);
            zaposlenikRepository.saveAdministrator(admin2);

            Blagajnik blag1 = Blagajnik.builder()
                    .korisnickoIme("blagajnik1")
                    .lozinka("password3")
                    .salt("salt3")
                    .imeZaposlenika("Ana")
                    .prezimeZaposlenika("Anić")
                    .uloga(Uloga.BLAGAJNIK)
                    .idBlagajnik("blagajnik01")
                    .radniDaniMjesec(20)
                    .build();

            Blagajnik blag2 = Blagajnik.builder()
                    .korisnickoIme("blagajnik2")
                    .lozinka("password4")
                    .salt("salt4")
                    .imeZaposlenika("Lucija")
                    .prezimeZaposlenika("Lucić")
                    .uloga(Uloga.BLAGAJNIK)
                    .idBlagajnik("blagajnik02")
                    .radniDaniMjesec(22)
                    .build();

            zaposlenikRepository.saveBlagajnik(blag1);
            zaposlenikRepository.saveBlagajnik(blag2);

            Film film = Film.builder()
                    .zaposlenik(admin1)
                    .naziv("Harry Potter and the Philosoper's Stone")
                    .trajanjeMin(120)
                    .dobnaGranica(12)
                    .ulazEur(5.70)
                    .build();

            filmRepository.save(film);

            Dvorana dvorana = Dvorana.builder()
                    .ime("dvorana01")
                    .zaposlenik(admin1)
                    .kapacitet(150)
                    .otvorena(true)
                    .build();

            dvoranaRepository.save(dvorana);

            Projekcija projekcija = Projekcija.builder()
                    .unioZaposlenik(admin1)
                    .trajanjeMin(120)
                    .vrijemePoc(Time.valueOf("18:00:00").toLocalTime())
                    .datum(LocalDate.now())
                    .slobodnaMjesta(140)
                    .film(film)
                    .dvorana(dvorana)
                    .build();

            projekcijaRepository.save(projekcija);
        };
    }
}
