package hr.fer.kinoprojekt.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data // obuhvaca @ToString, @EqualsAndHashCode, @Getter, @Setter and @RequiredArgsConstructor
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dvorana {
    @Id
    private String ime;
    

    private Integer kapacitet;
    private boolean otvorena;

    @OneToMany(mappedBy = "dvorana")
    private Set<Projekcija> projekcije;
}
