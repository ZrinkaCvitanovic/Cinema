package hr.fer.kinoprojekt.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Projekcija {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "imeDvorana", referencedColumnName = "ime")
    private Dvorana dvorana;

    @ManyToOne
    @JoinColumn(name = "idFilm", referencedColumnName = "id")
    private Film film;

    private Integer trajanjeMin = 0;
    private LocalTime vrijemePoc;
    private LocalDate datum;
    private Integer slobodnaMjesta;

    @ManyToOne
    @JoinColumn(name = "idTip", referencedColumnName = "id")
    private TipProjekcije tip;
}
