package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Route;
import br.com.danielbrai.core.entity.Voyage;

import java.util.List;

public class CreateShipmentUseCase {


    private AddCagoToShipmentUseCase addCagoToShipmentUseCase;

    public Voyage execute(List<Cargo> cargoList) {


        Route origin = mappedVoyage.getSchedule().stream().findFirst().orElseThrow();
        Route destination = mappedVoyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();

        Voyage voyage = Voyage.builder()
                .origin(origin.getOrigin())
                .destination(destination.getDestination())
                .capacity(mappedVoyage.getCapacity())
                .schedule(mappedVoyage.getSchedule())
                .cargo(new LinkedList<>())
                .build();

        cargoList.forEach(cargo -> this.addCagoToShipmentUseCase.execute(voyage, origin.getOrigin(), cargo));
        this.registerDeparture(voyage);
        return this.voyageRepository.save(voyage);
    }
}
