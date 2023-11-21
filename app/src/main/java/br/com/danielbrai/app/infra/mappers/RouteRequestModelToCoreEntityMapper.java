package br.com.danielbrai.app.infra.mappers;

import br.com.danielbrai.app.entrypoint.rest.model.request.RouteRequestModel;
import br.com.danielbrai.core.entity.Coordinate;
import br.com.danielbrai.core.entity.Route;
import jakarta.inject.Named;


@Named
public class RouteRequestModelToCoreEntityMapper implements Mapper<RouteRequestModel, Route> {
    @Override
    public Route map(RouteRequestModel input) {
        Coordinate origin = Coordinate.builder()
                .y(input.getYOrigin())
                .x(input.getXOrigin())
                .build();

        Coordinate destination = Coordinate.builder()
                .y(input.getYDestiny())
                .x(input.getXDestiny())
                .build();

        return Route.builder()
                .operation(input.getOperation())
                .destination(destination)
                .origin(origin)
                .build();
    }
}
