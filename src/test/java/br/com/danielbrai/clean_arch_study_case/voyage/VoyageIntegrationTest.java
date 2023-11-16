package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.cargo.CargoRequestModel;
import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import br.com.danielbrai.clean_arch_study_case.route.RouteRequestModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VoyageIntegrationTest {

    @Autowired
    VoyageService voyageService;

    @Autowired
    VoyageRepository repository;

    @Autowired
    VoyageController voyageController;


    @Test
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


        CargoRequestModel cargoRequestModelA = CargoRequestModel.builder()
                .capacity(5)
                .build();

        CargoRequestModel cargoRequestModelB = CargoRequestModel.builder()
                .capacity(5)
                .build();

        LinkedList<CargoRequestModel> cargo = new LinkedList<>();
        cargo.add(cargoRequestModelA);
        cargo.add(cargoRequestModelB);

        RouteRequestModel routeRequestModelA = RouteRequestModel.builder()
                .operation(Operations.LOAD)
                .xOrigin(-24.752646)
                .yOrigin(-47.572934)
                .xDestiny(-28.467)
                .yDestiny(-49.0075)
                .build();

        RouteRequestModel routeRequestModelB = RouteRequestModel.builder()
                .operation(Operations.LOAD)
                .xOrigin(-28.467)
                .yOrigin(-49.0075)
                .xDestiny(-2.44306)
                .yDestiny(-54.70833)
                .build();

        RouteRequestModel routeRequestModelC = RouteRequestModel.builder()
                .operation(Operations.LOAD)
                .xOrigin(-2.44306)
                .yOrigin(-54.70833)
                .xDestiny(51.5)
                .yDestiny(0.05)
                .build();

        LinkedHashSet<RouteRequestModel> routes = new LinkedHashSet<>();
        routes.add(routeRequestModelA);
        routes.add(routeRequestModelB);
        routes.add(routeRequestModelC);

        VoyageRequestModel requestModel = VoyageRequestModel.builder()
                .capacity(10)
                .cargo(cargo)
                .schedule(routes)
                .build();

        ResponseEntity<Voyage> voyageResponseEntity = this.voyageController.createVoyage(requestModel);
        Voyage voyage = voyageResponseEntity.getBody();

        Coordinate expectedOrigin = Coordinate.builder()
                .id(2L)
                .x(-24.752646)
                .y(-47.572934)
                .build();


        Coordinate expectedDestination = Coordinate.builder()
                .id(1L)
                .x(51.5)
                .y(0.05)
                .build();

        assertEquals(HttpStatusCode.valueOf(201), voyageResponseEntity.getStatusCode());
        assertEquals(2, voyage.getCargo().size());
        assertEquals(3, voyage.getSchedule().size());
        assertEquals(BigDecimal.valueOf(10.0), voyage.getCapacity());
        assertEquals(expectedOrigin, voyage.getOrigin());
        assertEquals(expectedDestination, voyage.getDestination());
    }
}
