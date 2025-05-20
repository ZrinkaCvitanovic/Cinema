package hr.fer.kinoprojekt.application.dto;

import hr.fer.kinoprojekt.domain.model.Film;
import lombok.Data;

@Data
public class SpremiFilmDto {
    private Integer id;
    private String naziv;
    private Integer trajanjeMin;
    private Integer dobnaGranica;
    private Double ulazEur;
    private Integer idRedatelj;

    public Film toDomain() {
        return Film.builder()
                .id(id)
                .naziv(naziv)
                .trajanjeMin(trajanjeMin)
                .dobnaGranica(dobnaGranica)
                .ulazEur(ulazEur)
                .build();
    }
}
