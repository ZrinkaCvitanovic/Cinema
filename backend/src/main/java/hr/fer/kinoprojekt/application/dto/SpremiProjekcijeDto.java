package hr.fer.kinoprojekt.application.dto;

import hr.fer.kinoprojekt.domain.model.Projekcija;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class SpremiProjekcijeDto {
    private String imeDvorana;
    private String unioProjekcija;
    private Integer idFilm;
    private Integer trajanjeMin;
    private String vrijemePoc;
    private String datum;
    private Integer slobodnaMjesta;

    public Projekcija toDomain() {
        return Projekcija.builder()
                .trajanjeMin(this.trajanjeMin)
                .vrijemePoc(LocalTime.parse(this.vrijemePoc))
                .datum(LocalDate.parse(this.datum))
                .slobodnaMjesta(this.slobodnaMjesta)
                .build();
    }
}
