package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.RedateljRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private RedateljRepository redateljRepository;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFilmovi_shouldReturnListOfFilms() {
        List<Film> mockFilms = List.of(
                Film.builder().id(1).build(),
                Film.builder().id(2).build()
        );
        when(filmRepository.getFilmovi()).thenReturn(mockFilms);

        List<Film> result = filmService.getFilmovi();

        assertEquals(2, result.size());
        verify(filmRepository, times(1)).getFilmovi();
    }

    @Test
    void getFilm_shouldReturnFilm() {
        Film film = Film.builder().id(1).build();
        when(filmRepository.getFilm(1)).thenReturn(film);

        Film result = filmService.getFilm(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(filmRepository, times(1)).getFilm(1);
    }

    @Test
    void save_shouldAssignRedateljAndSaveFilm() {
        Film film = Film.builder().build();
        Redatelj redatelj = Redatelj.builder().id(10).build();

        when(redateljRepository.getRedatelj(10)).thenReturn(redatelj);

        filmService.save(film, 10);

        assertEquals(redatelj, film.getRedatelj());
        verify(redateljRepository, times(1)).getRedatelj(10);
        verify(filmRepository, times(1)).save(film);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        filmService.delete(5);

        verify(filmRepository, times(1)).deletePoId(5);
    }
}
