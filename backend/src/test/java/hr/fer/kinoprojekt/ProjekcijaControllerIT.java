package hr.fer.kinoprojekt;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.fer.kinoprojekt.application.controller.ProjekcijaController;
import hr.fer.kinoprojekt.application.dto.DvoranaDto;
import hr.fer.kinoprojekt.application.dto.SpremiFilmDto;
import hr.fer.kinoprojekt.application.dto.SpremiProjekcijeDto;
import hr.fer.kinoprojekt.config.NoSecurityConfig;
import hr.fer.kinoprojekt.domain.model.*;
import hr.fer.kinoprojekt.domain.service.ProjekcijaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@Import(NoSecurityConfig.class)
@WebMvcTest(ProjekcijaController.class)
class ProjekcijaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProjekcijaService projekcijaService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProjekcijaService projekcijaService() {
            return Mockito.mock(ProjekcijaService.class);
        }
    }
    @Test
    void getAllProjekcija_shouldReturnList() throws Exception {
        DvoranaDto a1_dto = DvoranaDto.builder()
                .ime("TestDvorana")
                .kapacitet(150)
                .otvorena(true)
                .build();
        Dvorana a1_dvorana = a1_dto.toDomain();

        SpremiFilmDto dto = new SpremiFilmDto();
        dto.setNaziv("Test Film");
        dto.setTrajanjeMin(120);
        dto.setDobnaGranica(12);
        dto.setUlazEur(7.5);
        dto.setIdRedatelj(1234);

        TipProjekcije tip = TipProjekcije.builder().id(123).build();

        Projekcija projekcija = Projekcija.builder()
                .id("123")
                .dvorana(a1_dvorana)
                .film(dto.toDomain())
                .trajanjeMin(120)
                .vrijemePoc(LocalTime.of(20, 0))
                .datum(LocalDate.of(2025, 5, 23))
                .slobodnaMjesta(30)
                .tip(tip)
                .build();

        Mockito.when(projekcijaService.getProjekcije()).thenReturn(List.of(projekcija));

        mockMvc.perform(get("/api/projekcija/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("123"))
                .andExpect(jsonPath("$[0].imeDvorana").value("TestDvorana"));
    }

    @Test
    void getProjekcijaById_shouldReturnOne() throws Exception {
        DvoranaDto a1_dto = DvoranaDto.builder()
                .ime("TestDvorana")
                .kapacitet(150)
                .otvorena(true)
                .build();
        Dvorana a1_dvorana = a1_dto.toDomain();

        SpremiFilmDto dto = new SpremiFilmDto();
        dto.setNaziv("Test Film");
        dto.setTrajanjeMin(120);
        dto.setDobnaGranica(12);
        dto.setUlazEur(7.5);
        dto.setIdRedatelj(1234);

        TipProjekcije tip = TipProjekcije.builder().id(123).build();

        Projekcija projekcija = Projekcija.builder()
                .id("123")
                .dvorana(a1_dvorana)
                .film(dto.toDomain())
                .trajanjeMin(120)
                .vrijemePoc(LocalTime.of(20, 0))
                .datum(LocalDate.of(2025, 5, 23))
                .slobodnaMjesta(30)
                .tip(tip)
                .build();

        Mockito.when(projekcijaService.getProjekcijaPoId("123")).thenReturn(projekcija);

        mockMvc.perform(get("/api/projekcija/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("123"))
                .andExpect(jsonPath("$.trajanjeMin").value(120));
    }

    @Test
    void create_shouldReturnSuccess() throws Exception {
        SpremiProjekcijeDto dto = new SpremiProjekcijeDto();
        dto.setId("newid");
        dto.setImeDvorana("A1");
        dto.setIdFilm(1);
        dto.setTrajanjeMin(120);
        dto.setVrijemePoc("19:00");
        dto.setDatum("2025-05-25");
        dto.setSlobodnaMjesta(50);
        dto.setIdTip(1);

        Mockito.when(projekcijaService.checkAvailability(any())).thenReturn(true);

        mockMvc.perform(post("/api/projekcija")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void create_shouldReturnBadRequestIfOverlapping() throws Exception {
        SpremiProjekcijeDto dto = new SpremiProjekcijeDto();
        dto.setDatum("2025-05-25");
        dto.setVrijemePoc("19:00");
        dto.setIdFilm(1);

        Mockito.when(projekcijaService.checkAvailability(any())).thenReturn(false);

        mockMvc.perform(post("/api/projekcija")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Postoji vremensko preklapanje za ovaj film, odaberite novo vrijeme"));
    }

    @Test
    void delete_shouldReturnSuccess() throws Exception {
        mockMvc.perform(delete("/api/projekcija/abc123"))
                .andExpect(status().isOk())
                .andExpect(content().string("SUCCESS!"));

        Mockito.verify(projekcijaService).delete("abc123");
    }

    @Test
    void update_shouldReturnSuccess() throws Exception {
        SpremiProjekcijeDto dto = new SpremiProjekcijeDto();
        dto.setId("updateid");
        dto.setImeDvorana("A2");
        dto.setIdFilm(2);
        dto.setTrajanjeMin(100);
        dto.setVrijemePoc("17:00");
        dto.setDatum("2025-05-26");
        dto.setSlobodnaMjesta(40);
        dto.setIdTip(2);

        mockMvc.perform(put("/api/projekcija")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }
}
