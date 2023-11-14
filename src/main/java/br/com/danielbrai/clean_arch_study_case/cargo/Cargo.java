package br.com.danielbrai.clean_arch_study_case.cargo;

import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.voyage.Voyage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "cargo")
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal capacity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_LOAD_LOCATION")
    private Coordinate loadLocation;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_UNLOAD_LOCATION")
    private Coordinate unloadLocation;

    @ManyToOne
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Voyage voyage;
}
