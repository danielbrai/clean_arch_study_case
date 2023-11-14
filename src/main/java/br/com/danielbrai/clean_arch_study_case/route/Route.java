package br.com.danielbrai.clean_arch_study_case.route;

import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import br.com.danielbrai.clean_arch_study_case.voyage.Voyage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "route")
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORIGIN")
    private Coordinate origin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DESTINATION")
    private Coordinate destination;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private Operations operation;

    @ManyToOne
    private Voyage voyage;
}
