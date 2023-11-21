package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.cargo.CargoRequestModelToEntityMapper;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import br.com.danielbrai.clean_arch_study_case.route.RouteRequestModelToEntityMapper;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/voyage")
@AllArgsConstructor
public class VoyageController {

    private final VoyageService voyageService;

    private final CargoRequestModelToEntityMapper cargoRequestModelToEntityMapper;

    private final RouteRequestModelToEntityMapper routeRequestModelToEntityMapper;


    @PostMapping()
    ResponseEntity<Voyage> createVoyage(@RequestBody VoyageRequestModel requestModel) {
        List<Cargo> mappedCargo = requestModel.getCargo().stream().map(this.cargoRequestModelToEntityMapper::map).collect(Collectors.toCollection(LinkedList::new));
        Set<Route> mappedSchedule = requestModel.getSchedule().stream().map(this.routeRequestModelToEntityMapper::map).collect(Collectors.toCollection(LinkedHashSet::new));
        Voyage voyage = this.voyageService.createShipment(requestModel.getCapacity(), mappedSchedule, mappedCargo);
        return ResponseEntity.status(HttpStatus.CREATED).body(voyage);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Cargo> sendVoyage(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        Cargo cargo = this.voyageService.dropExcess(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
    }

    @GetMapping("/{id}")
    ResponseEntity<Voyage> getVoyage(@PathVariable Long id) {
        Voyage voyage = this.voyageService.retriveVoyage(id);
        return ResponseEntity.ok(voyage);
    }
}
