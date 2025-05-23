package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.repository.TipProjekcijeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TipProjekcijeServiceTest {

    private TipProjekcijeRepository repository;
    private TipProjekcijeService service;

    @BeforeEach
    void setUp() {
        repository = mock(TipProjekcijeRepository.class);
        service = new TipProjekcijeService(repository);
    }

    @Test
    void getTipovi_shouldReturnList() {
        TipProjekcije t1 = new TipProjekcije(1, "2D");
        TipProjekcije t2 = new TipProjekcije(2, "3D");

        when(repository.getTipoviProjekcije()).thenReturn(Arrays.asList(t1, t2));

        List<TipProjekcije> result = service.getTipoviProjekcije();

        assertEquals(2, result.size());
        assertEquals("3D", result.get(1).getTip());
    }

    @Test
    void getTip_shouldReturnCorrectTip() {
        TipProjekcije t = new TipProjekcije(1, "IMAX");

        when(repository.getTipProjekcije(1)).thenReturn(t);

        TipProjekcije result = service.getTipProjekcije(1);

        assertEquals("IMAX", result.getTip());
        assertEquals(1, result.getId());
    }

    @Test
    void save_shouldCallRepositorySave() {
        TipProjekcije t = new TipProjekcije(null, "4DX");

        service.save(t);

        verify(repository, times(1)).save(t);
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        service.delete(1);

        verify(repository, times(1)).deletePoId(1);
    }
}

