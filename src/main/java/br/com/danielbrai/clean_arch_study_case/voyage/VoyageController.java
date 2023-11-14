package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.LinkedList;

@RestController
@RequestMapping("/v1/voyage")
public class VoyageController {

    @Autowired
    VoyageService voyageService;

    @Autowired
    VoyageRepository repository;


    @PostMapping("")
    void shouldCreateAShipment() {

        Coordinate santosPort = Coordinate.builder()
                .x(-24.752646)
                .y(-47.572934)
                .build();
        Coordinate tubaraoPort = Coordinate.builder()
                .x(-28.467)
                .y(-49.0075)
                .build();

        Coordinate santaremPort = Coordinate.builder()
                .x(-2.44306)
                .y(-54.70833)
                .build();

        Coordinate felixstowePort = Coordinate.builder()
                .x(51.5)
                .y(0.05)
                .build();

        Route santosToTubarao= Route.builder()
                .operation(Operations.LOAD)
                .origin(santosPort)
                .destination(tubaraoPort)
                .build();

        Route tubaraoToSantarem = Route.builder()
                .operation(Operations.UNLOAD)
                .origin(tubaraoPort)
                .destination(santaremPort)
                .build();

        Route santaremToFelixStowe = Route.builder()
                .operation(Operations.LOAD)
                .origin(santaremPort)
                .destination(felixstowePort)
                .build();

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        LinkedList<Cargo> initialCargo = new LinkedList<>();
        initialCargo.add(c1);
        initialCargo.add(c2);

        LinkedHashSet<Route> schedule = new LinkedHashSet<>();
        schedule.add(santosToTubarao);
        schedule.add(tubaraoToSantarem);
        schedule.add(santaremToFelixStowe);

        BigDecimal shipCapacity = new BigDecimal(10);

        Voyage voyage = this.voyageService.createShipment(shipCapacity, initialCargo, schedule);
    }
}
