package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.cargo.CargoRequestModelToEntityMapper;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import br.com.danielbrai.clean_arch_study_case.route.RouteRequestModelToEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class VoyageRequestModelToEntityMapper {

    private final CargoRequestModelToEntityMapper cargoRequestModelToEntityMapper;

    private RouteRequestModelToEntityMapper routeRequestModelToEntityMapper;

    public Voyage map(VoyageRequestModel requestModel) {

        List<Cargo> mappedCargo = requestModel.getCargo().stream().map(this.cargoRequestModelToEntityMapper::map).collect(Collectors.toCollection(LinkedList::new));
        Set<Route> mappedSchedule = requestModel.getSchedule().stream().map(this.routeRequestModelToEntityMapper::map).collect(Collectors.toCollection(LinkedHashSet::new));

        return Voyage.builder()
                .schedule(mappedSchedule)
                .cargo(mappedCargo)
                .capacity(BigDecimal.valueOf(requestModel.getCapacity()))
                .build();
    }
}
