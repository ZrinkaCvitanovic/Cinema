package hr.fer.kinoprojekt;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RedateljControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedateljRepository redateljRepository;
    @Autowired
    private FilmRepository filmRepository;

    @BeforeEach
    void setup() {
        List<Integer> ids = filmRepository.getFilmovi().stream().map(Film::getId).toList();
        for (Integer el : ids) {
            filmRepository.deletePoId(el);
        }
        redateljRepository.getRedatelji().forEach(r -> redateljRepository.deletePoId(r.getId()));
    }

    @Test
    void getAllRedatelji_shouldReturnList() throws Exception {
        Redatelj r1 = new Redatelj(null, "Ivan", "Ivić");
        Redatelj r2 = new Redatelj(null, "Ana", "Anić");
        redateljRepository.save(r1);
        redateljRepository.save(r2);

        mockMvc.perform(get("/api/redatelj/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ivan")))
                .andExpect(jsonPath("$[1].surname", is("Anić")));
    }

    @Test
    void getRedateljById_shouldReturnOne() throws Exception {
        Redatelj saved = new Redatelj(null, "Luka", "Lukić");
        redateljRepository.save(saved);
        Integer id = redateljRepository.getRedatelji().get(0).getId();

        mockMvc.perform(get("/api/redatelj/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Luka")));
    }

    @Test
    void saveRedatelj_shouldReturnSuccess() throws Exception {
        Redatelj r = new Redatelj(null, "Maja", "Majić");

        mockMvc.perform(post("/api/redatelj")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        List<Redatelj> savedList = redateljRepository.getRedatelji();
        assert(savedList.stream().anyMatch(x -> x.getName().equals("Maja")));
    }

    @Test
    void deleteRedatelj_shouldRemoveEntity() throws Exception {
        Redatelj r = new Redatelj(null, "Zoran", "Zorić");
        redateljRepository.save(r);
        Integer id = redateljRepository.getRedatelji().get(0).getId();

        mockMvc.perform(delete("/api/redatelj/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        assert(redateljRepository.getRedatelji().isEmpty());
    }
}
