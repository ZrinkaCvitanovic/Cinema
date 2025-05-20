package hr.fer.kinoprojekt.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Zaposlenik {
    @Id
    private String korisnickoIme;
    private String lozinka;
    private String salt;
    private String imeZaposlenika;
    private String prezimeZaposlenika;

}