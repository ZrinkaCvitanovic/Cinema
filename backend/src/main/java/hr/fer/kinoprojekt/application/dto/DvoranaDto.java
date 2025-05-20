package hr.fer.kinoprojekt.application.dto;

import hr.fer.kinoprojekt.domain.model.Dvorana;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DvoranaDto {
    private String ime;
    private Integer kapacitet;
    private boolean otvorena;

    public static DvoranaDto fromDomain(Dvorana dvorana) {
        return DvoranaDto.builder()
                .ime(dvorana.getIme())
                .kapacitet(dvorana.getKapacitet())
                .otvorena(dvorana.isOtvorena())
                .build();
    }

    public Dvorana toDomain() {
        return Dvorana.builder()
                .ime(ime)
                .kapacitet(kapacitet)
                .otvorena(otvorena)
                .build();
    }
}
