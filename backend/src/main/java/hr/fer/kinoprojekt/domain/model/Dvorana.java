package hr.fer.kinoprojekt.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data // @ToString, @EqualsAndHashCode, @Getter, @Setter and @RequiredArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dvorana {
    @Id
    private String ime;

    @ManyToOne
    @JoinColumn(name = "unioZaposlenik", referencedColumnName = "korisnickoIme")
    private Zaposlenik zaposlenik;

    private Integer kapacitet;
    private boolean otvorena;

    @OneToMany(mappedBy = "dvorana")
    private Set<Projekcija> projekcije;
}
