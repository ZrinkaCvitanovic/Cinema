package hr.fer.kinoprojekt.application.dto;

import hr.fer.kinoprojekt.domain.model.Projekcija;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjekcijaDto {
    private String id;
    private String imeDvorana;
    private Integer idFilm;
    private Integer trajanjeMin;
    private String vrijemePoc;
    private String datum;
    private Integer slobodnaMjesta;
    private Integer idTip;

    public static ProjekcijaDto fromDomain(Projekcija projekcija) {
        return ProjekcijaDto.builder()
                .id(projekcija.getId())
                .imeDvorana(projekcija.getDvorana().getIme())
                .idFilm(projekcija.getFilm().getId())
                .trajanjeMin(projekcija.getTrajanjeMin())
                .vrijemePoc(projekcija.getVrijemePoc().toString())
                .datum(projekcija.getDatum().toString())
                .slobodnaMjesta(projekcija.getSlobodnaMjesta())
                .idTip(projekcija.getTip().getId())
                .build();
    }
}

