package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.application.dto.SpremiProjekcijeDto;
import hr.fer.kinoprojekt.domain.model.Dvorana;
import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Projekcija;
import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.ProjekcijaRepository;
import hr.fer.kinoprojekt.domain.repository.TipProjekcijeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjekcijaServiceTest {

    @Mock private ProjekcijaRepository projekcijaRepository;
    @Mock private DvoranaRepository dvoranaRepository;
    @Mock private FilmRepository filmRepository;
    @Mock private TipProjekcijeRepository tipProjekcijeRepository;

    @InjectMocks private ProjekcijaService projekcijaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProjekcije_shouldReturnList() {
        List<Projekcija> mockList = List.of(new Projekcija());
        when(projekcijaRepository.getProjekcije()).thenReturn(mockList);

        List<Projekcija> result = projekcijaService.getProjekcije();

        assertEquals(1, result.size());
        verify(projekcijaRepository, times(1)).getProjekcije();
    }

    @Test
    void getProjekcijaPoId_shouldReturnProjekcija() {
        Projekcija mock = new Projekcija();
        when(projekcijaRepository.getProjekcija("id123")).thenReturn(mock);

        Projekcija result = projekcijaService.getProjekcijaPoId("id123");

        assertNotNull(result);
        verify(projekcijaRepository).getProjekcija("id123");
    }

    @Test
    void save_shouldAssignEntitiesAndSave() {
        Projekcija projekcija = new Projekcija();

        Dvorana dvorana = new Dvorana();
        Film film = new Film();
        TipProjekcije tip = new TipProjekcije();

        when(dvoranaRepository.getDvorana("A1")).thenReturn(dvorana);
        when(filmRepository.getFilm(1)).thenReturn(film);
        when(tipProjekcijeRepository.getTipProjekcije(2)).thenReturn(tip);

        projekcijaService.save(projekcija, "A1", 1, 2);

        assertEquals(dvorana, projekcija.getDvorana());
        assertEquals(film, projekcija.getFilm());
        assertEquals(tip, projekcija.getTip());

        verify(projekcijaRepository).save(projekcija);
    }

    @Test
    void delete_shouldCallRepository() {
        projekcijaService.delete("abc123");

        verify(projekcijaRepository).deletePoId("abc123");
    }

    @Test
    void filterByDvorana_shouldCallRepository() {
        projekcijaService.filterByDvorana("A1");

        verify(projekcijaRepository).filterByDvorana("A1");
    }

    @Test
    void filterByFilm_shouldCallRepository() {
        projekcijaService.filterByFilm(99);

        verify(projekcijaRepository).filterByFilm(99);
    }

    @Test
    void checkAvailability_shouldReturnTrueIfNotBooked() {
        SpremiProjekcijeDto dto = new SpremiProjekcijeDto();
        dto.setDatum("2025-05-23");
        dto.setVrijemePoc("18:00");
        dto.setIdFilm(5);

        Projekcija p1 = new Projekcija();
        p1.setDatum(LocalDate.of(2025, 5, 23));
        p1.setVrijemePoc(LocalTime.of(16, 0));

        when(projekcijaRepository.filterByFilm(5)).thenReturn(List.of(p1));

        boolean result = projekcijaService.checkAvailability(dto);

        assertTrue(result);
    }

    @Test
    void checkAvailability_shouldReturnFalseIfTimeAlreadyBooked() {
        SpremiProjekcijeDto dto = new SpremiProjekcijeDto();
        dto.setDatum("2025-05-23");
        dto.setVrijemePoc("18:00");
        dto.setIdFilm(5);

        Projekcija p1 = new Projekcija();
        p1.setDatum(LocalDate.of(2025, 5, 23));
        p1.setVrijemePoc(LocalTime.of(18, 0));

        when(projekcijaRepository.filterByFilm(5)).thenReturn(List.of(p1));

        boolean result = projekcijaService.checkAvailability(dto);

        assertFalse(result);
    }
}
