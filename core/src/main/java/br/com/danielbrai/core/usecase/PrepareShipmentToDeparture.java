package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.domain.Cargo;
import br.com.danielbrai.core.domain.Route;
import br.com.danielbrai.core.domain.Voyage;
import br.com.danielbrai.core.exceptions.InvalidArgumentException;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@AllArgsConstructor
@Named
public class PrepareShipmentToDeparture {

    private final AddCargoToShipmentUseCase addCargoToShipmentUseCase;

    private final RegisterDepartureUseCase registerDepartureUseCase;

    public Voyage execute(double capacity, Set<Route> schedule, List<Cargo> cargos) {

        if(Objects.isNull(schedule) || schedule.isEmpty() || schedule.size() < 2) {
            throw new InvalidArgumentException("The schedule must have at least two routes!");
        }

        if(Objects.isNull(cargos) || cargos.isEmpty()) {
            throw new InvalidArgumentException("The initial cargo must have at least one cargo!");
        }

        Route origin = schedule.stream().findFirst().orElseThrow();
        Route destination = schedule.stream().reduce((a, b) -> b).orElseThrow();

        Voyage voyage = Voyage.builder()
                .origin(origin.getOrigin())
                .destination(destination.getDestination())
                .capacity(BigDecimal.valueOf(capacity))
                .schedule(schedule)
                .cargo(new LinkedList<>())
                .build();

        cargos.forEach(cargo -> this.addCargoToShipmentUseCase.execute(voyage, origin.getOrigin(), cargo));
        this.registerDepartureUseCase.execute(voyage);
        return voyage;
    }

}
