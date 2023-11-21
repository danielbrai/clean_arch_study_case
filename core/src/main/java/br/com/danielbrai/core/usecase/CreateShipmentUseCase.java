package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.dataprovider.VoyageDataProvider;
import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Route;
import br.com.danielbrai.core.entity.Voyage;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@Named
@AllArgsConstructor
public class CreateShipmentUseCase {

    @Named("voyageDataBaseDataProvider")
    private final VoyageDataProvider voyageDataBaseDataProvider;

    @Named
    private final PrepareShipmentToDeparture prepareShipmentToDeparture;

    public Voyage execute(double capacity, Set<Route> schedule, List<Cargo> cargos) {
        Voyage voyage = this.prepareShipmentToDeparture.execute(capacity, schedule, cargos);
        return this.voyageDataBaseDataProvider.save(voyage);
    }
}
