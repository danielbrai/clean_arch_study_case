package br.com.danielbrai.clean_arch_study_case.route;

import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import org.springframework.stereotype.Component;

@Component
public class RouteRequestModelToEntityMapper {

    public Route map(RouteRequestModel requestModel) {

        Coordinate origin = Coordinate.builder()
                .y(requestModel.getYOrigin())
                .x(requestModel.getXOrigin())
                .build();

        Coordinate destination = Coordinate.builder()
                .y(requestModel.getYDestiny())
                .x(requestModel.getXDestiny())
                .build();

        return Route.builder()
                .operation(requestModel.getOperation())
                .destination(destination)
                .origin(origin)
                .build();
    }
}
