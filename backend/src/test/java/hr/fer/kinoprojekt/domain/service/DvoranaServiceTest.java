package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Dvorana;
import hr.fer.kinoprojekt.domain.repository.DvoranaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DvoranaServiceTest {

    private DvoranaRepository dvoranaRepository;
    private DvoranaService dvoranaService;

    @BeforeEach
    void setUp() {
        dvoranaRepository = mock(DvoranaRepository.class);
        dvoranaService = new DvoranaService(dvoranaRepository);
    }

    @Test
    void testGetDvorane_ReturnsList() {
        List<Dvorana> expectedList = Arrays.asList(
                Dvorana.builder()
                        .ime("Dvorana A")
                        .kapacitet(100)
                        .otvorena(true)
                        .projekcije(new HashSet<>())
                        .build(),
                Dvorana.builder()
                        .ime("Dvorana B")
                        .kapacitet(200)
                        .otvorena(false)
                        .projekcije(new HashSet<>())
                        .build()
        );

        when(dvoranaRepository.getDvorane()).thenReturn(expectedList);

        List<Dvorana> actualList = dvoranaService.getDvorane();

        assertEquals(expectedList, actualList);
        verify(dvoranaRepository, times(1)).getDvorane();
    }

    @Test
    void testGetDvorana_ReturnsCorrectDvorana() {
        Dvorana expected = Dvorana.builder()
                .ime("Dvorana A")
                .kapacitet(150)
                .otvorena(true)
                .projekcije(new HashSet<>())
                .build();

        when(dvoranaRepository.getDvorana("Dvorana A")).thenReturn(expected);

        Dvorana actual = dvoranaService.getDvorana("Dvorana A");

        assertEquals(expected, actual);
        verify(dvoranaRepository, times(1)).getDvorana("Dvorana A");
    }

    @Test
    void testSave_CallsRepositorySave() {
        Dvorana dvorana = Dvorana.builder()
                .ime("Dvorana A")
                .kapacitet(120)
                .otvorena(true)
                .projekcije(new HashSet<>())
                .build();

        dvoranaService.save(dvorana);

        verify(dvoranaRepository, times(1)).save(dvorana);
    }

    @Test
    void testDelete_CallsRepositoryDeletePoNazivu() {
        String ime = "Dvorana A";

        dvoranaService.delete(ime);

        verify(dvoranaRepository, times(1)).deletePoNazivu(ime);
    }
}
