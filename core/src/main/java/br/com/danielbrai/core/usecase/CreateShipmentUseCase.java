package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.dataprovider.VoyageDataProvider;
import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Route;
import br.com.danielbrai.core.entity.Voyage;
import lombok.AllArgsConstructor;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Named
@AllArgsConstructor
public class CreateShipmentUseCase {


    private final AddCagoToShipmentUseCase addCagoToShipmentUseCase;

    private final RegisterDepartureUseCase registerDepartureUseCase;

    private final VoyageDataProvider voyageDataProvider;

    public Voyage createShipment(double capacity, Set<Route> schedule, List<Cargo> cargos) {

        Route origin = schedule.stream().findFirst().orElseThrow();
        Route destination = schedule.stream().reduce((a, b) -> b).orElseThrow();

        Voyage voyage = Voyage.builder()
                .origin(origin.getOrigin())
                .destination(destination.getDestination())
                .capacity(BigDecimal.valueOf(capacity))
                .schedule(schedule)
                .cargo(new LinkedList<>())
                .build();

        cargos.forEach(cargo -> this.addCagoToShipmentUseCase.execute(voyage, origin.getOrigin(), cargo));
        this.registerDepartureUseCase.execute(voyage);
        return this.voyageDataProvider.save(voyage);
    }

}
