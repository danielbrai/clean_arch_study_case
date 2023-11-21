package br.com.danielbrai.app.infra.mappers;

import br.com.danielbrai.app.dataprovider.db.table.CargoEntity;
import br.com.danielbrai.app.dataprovider.db.table.CoordinateEntity;
import br.com.danielbrai.app.dataprovider.db.table.RouteEntity;
import br.com.danielbrai.app.dataprovider.db.table.VoyageEntity;
import br.com.danielbrai.core.domain.Voyage;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Component
public class VoyageCoreDomainToEntityModelMapper implements Mapper<Voyage, VoyageEntity> {
    @Override
    public VoyageEntity map(Voyage input) {

        CoordinateEntity destination = CoordinateEntity.builder()
                .y(input.getDestination().getY())
                .x(input.getDestination().getX())
                .build();

        CoordinateEntity origin = CoordinateEntity.builder()
                .y(input.getOrigin().getY())
                .x(input.getOrigin().getX())
                .build();

        LinkedHashSet<RouteEntity> routeEntities = input.getSchedule().stream().map(route -> {
            CoordinateEntity originRoute = CoordinateEntity.builder()
                    .y(route.getOrigin().getY())
                    .x(route.getOrigin().getX())
                    .build();
            CoordinateEntity destinationRoute = CoordinateEntity.builder()
                    .y(route.getDestination().getY())
                    .x(route.getDestination().getX())
                    .build();
            return RouteEntity.builder()
                    .operation(route.getOperation())
                    .arrival(route.getArrival())
                    .departure(route.getDeparture())
                    .origin(originRoute)
                    .destination(destinationRoute)
                    .build();
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        VoyageEntity voyageEntity = VoyageEntity.builder()
                .destination(destination)
                .origin(origin)
                .schedule(routeEntities)
                .capacity(input.getCapacity())
                .build();

        LinkedList<CargoEntity> cargoEntities = input.getCargo().stream().map(cargo -> {

            CoordinateEntity loadLocation = CoordinateEntity.builder()
                    .id(cargo.getId())
                    .x(cargo.getLoadLocation().getX())
                    .y(cargo.getLoadLocation().getY())
                    .build();

            return CargoEntity.builder()
                    .capacity(cargo.getCapacity())
                    .loadLocation(loadLocation)
                    .build();
        }).collect(Collectors.toCollection(LinkedList::new));

        cargoEntities.forEach(voyageEntity::addCargo);


        return voyageEntity;

    }
}
