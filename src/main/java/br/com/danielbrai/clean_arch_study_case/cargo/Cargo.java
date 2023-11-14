package br.com.danielbrai.clean_arch_study_case.cargo;

import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.voyage.Voyage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Voyage voyage;
}
