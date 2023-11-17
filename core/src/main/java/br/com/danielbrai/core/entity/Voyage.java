package br.com.danielbrai.core.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class Voyage {

    private Long id;

    private Coordinate origin;

    private Coordinate destination;

    private List<Cargo> cargo;

    private Set<Route> schedule;

    private BigDecimal capacity;

    public void addCargo(Cargo cargo) {
        this.cargo.add(cargo);
    }

    public void addRoute(Route route) {
        this.schedule.add(route);
    }
}
