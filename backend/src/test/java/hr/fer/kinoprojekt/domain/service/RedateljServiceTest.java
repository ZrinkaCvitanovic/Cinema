package hr.fer.kinoprojekt.domain.service;

import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.repository.RedateljRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedateljServiceTest {

    private RedateljRepository repository;
    private RedateljService service;

    @BeforeEach
    void setUp() {
        repository = mock(RedateljRepository.class);
        service = new RedateljService(repository);
    }

    @Test
    void getRedatelji_shouldReturnList() {
        Redatelj r1 = Redatelj.builder().id(1).name("Ivan").surname("Ivić").build();
        Redatelj r2 = Redatelj.builder().id(2).name("Ana").surname("Anić").build();

        when(repository.getRedatelji()).thenReturn(Arrays.asList(r1, r2));

        List<Redatelj> result = service.getRedatelji();

        assertEquals(2, result.size());
        assertEquals("Ivan", result.get(0).getName());
        verify(repository).getRedatelji();
    }

    @Test
    void getRedatelj_shouldReturnRedatelj() {
        Redatelj r = Redatelj.builder().id(1).name("Marko").surname("Marić").build();

        when(repository.getRedatelj(1)).thenReturn(r);

        Redatelj result = service.getRedatelj(1);

        assertNotNull(result);
        assertEquals("Marko", result.getName());
        verify(repository).getRedatelj(1);
    }

    @Test
    void save_shouldCallRepositorySave() {
        Redatelj r = Redatelj.builder().id(3).name("Luka").surname("Lukić").build();

        service.save(r);

        ArgumentCaptor<Redatelj> captor = ArgumentCaptor.forClass(Redatelj.class);
        verify(repository).save(captor.capture());

        Redatelj saved = captor.getValue();
        assertEquals("Luka", saved.getName());
    }

    @Test
    void delete_shouldCallRepositoryDelete() {
        service.delete(5);

        verify(repository).deletePoId(5);
    }
}
