package hr.fer.kinoprojekt.application.dto;

import hr.fer.kinoprojekt.domain.model.Film;
import hr.fer.kinoprojekt.domain.model.Redatelj;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilmDto {
    private Integer id;
    private String naziv;
    private Integer trajanjeMin;
    private Integer dobnaGranica;
    private Double ulazEur;
    private Redatelj redatelj;
    private List<ProjekcijaDto> projekcijaDtoList;

    public static FilmDto fromDomain(Film domain) {
        return FilmDto.builder()
                .id(domain.getId())
                .naziv(domain.getNaziv())
                .trajanjeMin(domain.getTrajanjeMin())
                .dobnaGranica(domain.getDobnaGranica())
                .ulazEur(domain.getUlazEur())
                .redatelj(domain.getRedatelj())
                .build();
    }
}
