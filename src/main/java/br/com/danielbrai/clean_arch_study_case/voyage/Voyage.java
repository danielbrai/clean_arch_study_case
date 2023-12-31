package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
@Entity
@Table(name = "voyage")
@AllArgsConstructor
@NoArgsConstructor
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Coordinate origin;

    @ManyToOne(cascade = CascadeType.ALL)
    private Coordinate destination;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voyage", orphanRemoval = true)
    private List<Cargo> cargo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "VOYAGE_ID")
    private Set<Route> schedule;

    private BigDecimal capacity;

    public void addCargo(Cargo cargo) {
        cargo.setVoyage(this);
        this.cargo.add(cargo);
    }

    public void dropCargo(Cargo cargo) {
        cargo.setVoyage(null);
        this.cargo.remove(cargo);
    }

    public void addRoute(Route route) {
        route.setVoyage(this);
        this.schedule.add(route);
    }
}
