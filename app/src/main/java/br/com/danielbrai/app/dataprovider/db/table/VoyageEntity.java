package br.com.danielbrai.app.dataprovider.db.table;

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
public class VoyageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private CoordinateEntity origin;

    @ManyToOne(cascade = CascadeType.ALL)
    private CoordinateEntity destination;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "voyage")
    private List<CargoEntity> cargo;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "VOYAGE_ID")
    private Set<RouteEntity> schedule;

    private BigDecimal capacity;

    public void addCargo(CargoEntity cargo) {
        cargo.setVoyage(this);
        this.cargo.add(cargo);
    }

    public void addRoute(RouteEntity route) {
        route.setVoyage(this);
        this.schedule.add(route);
    }
}
