package br.com.danielbrai.app.infra.mappers;

import br.com.danielbrai.app.dataprovider.db.table.VoyageEntity;
import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Coordinate;
import br.com.danielbrai.core.entity.Route;
import br.com.danielbrai.core.entity.Voyage;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class VoyageEntityToDomainModelMapper implements Mapper<VoyageEntity, Voyage> {
    @Override
    public Voyage map(VoyageEntity input) {

        LinkedHashSet<Route> mappedSchedule = input.getSchedule().stream().map(route -> {
            Coordinate origin = Coordinate.builder()
                    .x(route.getOrigin().getX())
                    .y(route.getOrigin().getY())
                    .build();
            Coordinate destination = Coordinate.builder()
                    .x(route.getDestination().getX())
                    .y(route.getDestination().getY())
                    .build();
            return Route.builder().id(route.getId())
                    .arrival(route.getArrival())
                    .destination(destination)
                    .origin(origin)
                    .departure(route.getDeparture())
                    .operation(route.getOperation())
                    .build();
        }).collect(Collectors.toCollection(LinkedHashSet::new));

        List<Cargo> mappedCargo = input.getCargo().stream().map(cargo -> {
            Coordinate loadLocation = Coordinate.builder()
                    .id(Objects.nonNull(cargo.getLoadLocation()) ? cargo.getLoadLocation().getId() : null)
                    .y(Objects.nonNull(cargo.getLoadLocation()) ? cargo.getLoadLocation().getY() : null)
                    .x(Objects.nonNull(cargo.getLoadLocation()) ? cargo.getLoadLocation().getX() : null)
                    .build();
            Coordinate unloadLocation = Coordinate.builder()
                    .id(Objects.nonNull(cargo.getUnloadLocation()) ? cargo.getUnloadLocation().getId() : null)
                    .y(Objects.nonNull(cargo.getUnloadLocation()) ? cargo.getUnloadLocation().getY() : null)
                    .x(Objects.nonNull(cargo.getUnloadLocation()) ? cargo.getUnloadLocation().getX() : null)
                    .build();
            return Cargo.builder()
                    .id(cargo.getId())
                    .capacity(cargo.getCapacity())
                    .loadLocation(loadLocation)
                    .unloadLocation(unloadLocation)
                    .build();
        }).collect(Collectors.toCollection(LinkedList::new));

        Coordinate origin = Coordinate.builder()
                .y(input.getOrigin().getY())
                .x(input.getOrigin().getX())
                .id(input.getId())
                .build();

        Coordinate destination = Coordinate.builder()
                .y(input.getOrigin().getY())
                .x(input.getOrigin().getX())
                .id(input.getId())
                .build();

        return Voyage.builder()
                .capacity(input.getCapacity())
                .id(input.getId())
                .schedule(mappedSchedule)
                .cargo(mappedCargo)
                .destination(destination)
                .origin(origin)
                .build();
    }
}
