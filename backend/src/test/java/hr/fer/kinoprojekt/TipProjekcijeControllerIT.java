package hr.fer.kinoprojekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.kinoprojekt.domain.model.TipProjekcije;
import hr.fer.kinoprojekt.domain.repository.TipProjekcijeRepository;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TipProjekcijeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TipProjekcijeRepository tipProjekcijeRepository;

    @BeforeEach
    void setup() {
        tipProjekcijeRepository.getTipoviProjekcije().forEach(
                t -> tipProjekcijeRepository.deletePoId(t.getId())
        );
    }

    @Test
    void getAllTipovi_shouldReturnList() throws Exception {
        TipProjekcije t1 = new TipProjekcije(null, "2D");
        TipProjekcije t2 = new TipProjekcije(null, "3D");
        tipProjekcijeRepository.save(t1);
        tipProjekcijeRepository.save(t2);

        mockMvc.perform(get("/api/tip/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tip", is("2D")))
                .andExpect(jsonPath("$[1].tip", is("3D")));
    }

    @Test
    void getTipById_shouldReturnTip() throws Exception {
        TipProjekcije t = new TipProjekcije(null, "IMAX");
        tipProjekcijeRepository.save(t);
        Integer id = tipProjekcijeRepository.getTipoviProjekcije().get(0).getId();

        mockMvc.perform(get("/api/tip/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tip", is("IMAX")));
    }

    @Test
    void saveTip_shouldReturnSuccess() throws Exception {
        TipProjekcije t = new TipProjekcije(null, "4DX");

        mockMvc.perform(post("/api/tip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(t)))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        List<TipProjekcije> saved = tipProjekcijeRepository.getTipoviProjekcije();
        assertTrue(saved.stream().anyMatch(x -> x.getTip().equals("4DX")));
    }

    @Test
    void deleteTip_shouldRemoveTip() throws Exception {
        TipProjekcije t = new TipProjekcije(null, "Silent");
        tipProjekcijeRepository.save(t);
        Integer id = tipProjekcijeRepository.getTipoviProjekcije().get(0).getId();

        mockMvc.perform(delete("/api/tip/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        assertTrue(tipProjekcijeRepository.getTipoviProjekcije().isEmpty());
    }
}

