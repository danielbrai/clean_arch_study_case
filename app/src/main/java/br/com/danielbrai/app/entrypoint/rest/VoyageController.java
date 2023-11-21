package br.com.danielbrai.app.entrypoint.rest;

import br.com.danielbrai.app.entrypoint.rest.model.request.VoyageRequestModel;
import br.com.danielbrai.app.infra.mappers.CargoRequestModelToCoreEntityMapper;
import br.com.danielbrai.app.infra.mappers.RouteRequestModelToCoreEntityMapper;
import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Route;
import br.com.danielbrai.core.entity.Voyage;
import br.com.danielbrai.core.usecase.CreateShipmentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/voyage")
@AllArgsConstructor
public class VoyageController {

    private final CargoRequestModelToCoreEntityMapper cargoRequestModelToCoreEntityMapper;

    private final RouteRequestModelToCoreEntityMapper routeRequestModelToCoreEntityMapper;

    private final CreateShipmentUseCase createShipmentUseCase;

    @PostMapping()
    public ResponseEntity<Voyage> createVoyage(@RequestBody VoyageRequestModel requestModel) {
        List<Cargo> mappedCargo = requestModel.getCargo().stream().map(this.cargoRequestModelToCoreEntityMapper::map).collect(Collectors.toCollection(LinkedList::new));
        Set<Route> mappedSchedule = requestModel.getSchedule().stream().map(this.routeRequestModelToCoreEntityMapper::map).collect(Collectors.toCollection(LinkedHashSet::new));
        Voyage voyage = this.createShipmentUseCase.execute(requestModel.getCapacity(), mappedSchedule, mappedCargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(voyage);
    }

}
