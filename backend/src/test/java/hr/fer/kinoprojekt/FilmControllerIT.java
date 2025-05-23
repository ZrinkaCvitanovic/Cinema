package hr.fer.kinoprojekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.kinoprojekt.application.dto.FilmDto;
import hr.fer.kinoprojekt.application.dto.SpremiFilmDto;
import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Redatelj;
import hr.fer.kinoprojekt.domain.repository.FilmRepository;
import hr.fer.kinoprojekt.domain.repository.RedateljRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FilmControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RedateljRepository redateljRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Redatelj testRedatelj;

    @BeforeEach
    void setUp() {
        // Clean existing data
        filmRepository.getFilmovi().forEach(f -> filmRepository.deletePoId(f.getId()));
        redateljRepository.getRedatelji().forEach(r -> redateljRepository.deletePoId(r.getId()));

        Redatelj redatelj = new Redatelj();
        redatelj.setName("Ivan");
        redatelj.setSurname("Horvat");
        redateljRepository.save(redatelj);

        // Now it should have an ID
        testRedatelj = redateljRepository.getRedatelji().get(0);
    }

    @Test
    void getAllFilms_shouldReturnEmptyListInitially() throws Exception {
        mockMvc.perform(get("/api/film/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createFilm_shouldSaveAndReturnSuccess() throws Exception {
        SpremiFilmDto dto = new SpremiFilmDto();
        dto.setNaziv("Test Film");
        dto.setTrajanjeMin(120);
        dto.setDobnaGranica(12);
        dto.setUlazEur(7.5);
        dto.setIdRedatelj(testRedatelj.getId());

        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        List<Film> films = filmRepository.getFilmovi();
        assert(films.size() == 1);
        assert(films.get(0).getNaziv().equals("Test Film"));
        assert(films.get(0).getRedatelj().getId().equals(testRedatelj.getId()));
    }

    @Test
    void getFilmById_shouldReturnFilmWithProjections() throws Exception {
        // First save a film directly
        Film film = Film.builder()
                .naziv("Film 1")
                .trajanjeMin(100)
                .dobnaGranica(15)
                .ulazEur(6.0)
                .redatelj(testRedatelj)
                .build();
        filmRepository.save(film);

        mockMvc.perform(get("/api/film/" + film.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv").value("Film 1"))
                .andExpect(jsonPath("$.redatelj.id").value(testRedatelj.getId()));
    }

    @Test
    void updateFilm_shouldModifyExistingFilm() throws Exception {
        Film film = Film.builder()
                .naziv("Old Name")
                .trajanjeMin(90)
                .dobnaGranica(10)
                .ulazEur(5.0)
                .redatelj(testRedatelj)
                .build();
        filmRepository.save(film);

        SpremiFilmDto updateDto = new SpremiFilmDto();
        updateDto.setId(film.getId());
        updateDto.setNaziv("Updated Name");
        updateDto.setTrajanjeMin(110);
        updateDto.setDobnaGranica(12);
        updateDto.setUlazEur(6.5);
        updateDto.setIdRedatelj(testRedatelj.getId());

        mockMvc.perform(put("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        Film updated = filmRepository.getFilm(film.getId());
        assertEquals("Updated Name", updated.getNaziv());
        assertEquals(110, updated.getTrajanjeMin());
    }

    @Test
    void deleteFilm_shouldRemoveFilm() throws Exception {
        Film film = Film.builder()
                .naziv("To be deleted")
                .trajanjeMin(80)
                .dobnaGranica(10)
                .ulazEur(4.0)
                .redatelj(testRedatelj)
                .build();
        filmRepository.save(film);

        mockMvc.perform(delete("/api/film/" + film.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        // After deletion, film should not exist
        assertThrows(Exception.class, () -> filmRepository.getFilm(film.getId()));
    }
}
