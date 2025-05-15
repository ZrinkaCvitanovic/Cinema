package hr.fer.kinoprojekt.application.dto;

import hr.fer.kinoprojekt.domain.model.Film;
import lombok.Data;

@Data
public class SpremiFilmDto {
    private String naziv;
    private Integer trajanjeMin;
    private Integer dobnaGranica;
    private Double ulazEur;
    private String unioZaposlenik;

    public Film toDomain() {
        return Film.builder()
                .naziv(naziv)
                .trajanjeMin(trajanjeMin)
                .dobnaGranica(dobnaGranica)
                .ulazEur(ulazEur)
                /*.unioZaposlenik(unioZaposlenik) ??*/
                .build();
    }
}
