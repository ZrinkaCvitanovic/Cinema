package hr.fer.kinoprojekt.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "unioFilm", referencedColumnName = "korisnickoIme")
    private Zaposlenik zaposlenik;

    @Column(unique = true)
    private String naziv;
    private Integer trajanjeMin;
    private Integer dobnaGranica;
    private Double ulazEur;
}
