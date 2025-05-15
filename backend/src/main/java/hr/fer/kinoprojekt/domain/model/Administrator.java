package hr.fer.kinoprojekt.domain.model;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) //zovem equals() i hashCode() od Administrator, ne od Zaposlenik
public class Administrator extends Zaposlenik {
    private String idAdmin;
    private Integer radniSatiMjesec;
}
