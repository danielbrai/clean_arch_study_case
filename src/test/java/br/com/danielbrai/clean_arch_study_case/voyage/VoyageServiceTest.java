package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoyageServiceTest {

    @InjectMocks
    VoyageService voyageService;

    @Mock
    VoyageRepository voyageRepository;

    @Mock
    VoyageRequestModelToEntityMapper voyageRequestModelToEntityMapper;

    @Captor
    private ArgumentCaptor<Voyage> voyageArgumentCaptor;

    @Test
    void shouldDropExcessWhenTheTargetCargoIsTheFirstInArray() {

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c3 = Cargo.builder()
                .capacity(new BigDecimal(2))
                .build();

        Cargo c4 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c5 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c6 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        List<Cargo> inputCargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));

        Cargo cargo = this.voyageService.dropExcess(inputCargo, BigDecimal.valueOf(21));

        assertEquals(c1, cargo);

    }

    @Test
    void shouldDropExcessWhenTheTargetCargoIsAtTheMiddleOfList() {

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c3 = Cargo.builder()
                .capacity(new BigDecimal(2))
                .build();

        Cargo c4 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c5 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c6 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        List<Cargo> inputCargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));

        Cargo cargo = this.voyageService.dropExcess(inputCargo, BigDecimal.valueOf(22));

        assertEquals(c4, cargo);
    }

    @Test
    void shouldDropExcessWhenTheTargetCargoIsAtTheEndOfList() {

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c3 = Cargo.builder()
                .capacity(new BigDecimal(2))
                .build();

        Cargo c4 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c5 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c6 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        List<Cargo> inputCargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5, c6));

        Cargo cargo = this.voyageService.dropExcess(inputCargo, BigDecimal.valueOf(21));

        assertEquals(c6, cargo);

    }

    @Test
    void shouldAcceptMoreCargoIfShipmentCanCarry() {

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c3 = Cargo.builder()
                .capacity(new BigDecimal(2))
                .build();

        Cargo c4 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c5 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c6 = Cargo.builder()
                .capacity(new BigDecimal(2))
                .build();

        List<Cargo> cargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Coordinate santosHarbour = Coordinate.builder()
                .x(-24.752646)
                .y(-47.572934)
                .build();

        Route route = Route.builder()
                .destination(santosHarbour)
                .arrival(LocalDateTime.now())
                .build();

        HashSet<Route> routes = new HashSet<>();
        routes.add(route);

        Voyage voyage = Voyage.builder()
                .cargo(cargo)
                .capacity(new BigDecimal(22))
                .schedule(routes)
                .build();

        this.voyageService.makeStepOverToLoad(voyage, null, c6);

        assertEquals(6, voyage.getCargo().size());

    }

    @Test
    void shouldAccepAnOverbookingCargoShipmentCanCarry() {

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c3 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c4 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c5 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c6 = Cargo.builder()
                .capacity(new BigDecimal(1))
                .build();

        List<Cargo> cargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5));

        Coordinate santosHarbour = Coordinate.builder()
                .x(-24.752646)
                .y(-47.572934)
                .build();

        Route route = Route.builder()
                .destination(santosHarbour)
                .arrival(LocalDateTime.now())
                .build();

        HashSet<Route> routes = new HashSet<>();
        routes.add(route);

        Voyage voyage = Voyage.builder()
                .cargo(cargo)
                .capacity(new BigDecimal(22))
                .schedule(routes)
                .build();

        this.voyageService.makeStepOverToLoad(voyage, null, c6);

        assertEquals(6, voyage.getCargo().size());

    }

    @Test
    void shouldnotAcceptMoreCargoIfShipmentIsFullyLoaded() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Cargo c1 = Cargo.builder()
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c2 = Cargo.builder()
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c3 = Cargo.builder()
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c4 = Cargo.builder()
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c5 = Cargo.builder()
                    .capacity(new BigDecimal(1))
                    .build();

            Cargo c6 = Cargo.builder()
                    .capacity(new BigDecimal(5))
                    .build();

            List<Cargo> cargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5));

            Coordinate santosHarbour = Coordinate.builder()
                    .x(-24.752646)
                    .y(-47.572934)
                    .build();

            Route route = Route.builder()
                    .destination(santosHarbour)
                    .arrival(LocalDateTime.now())
                    .build();

            HashSet<Route> routes = new HashSet<>();
            routes.add(route);

            Voyage voyage = Voyage.builder()
                    .cargo(cargo)
                    .capacity(new BigDecimal(22))
                    .schedule(routes)
                    .build();

            this.voyageService.makeStepOverToLoad(voyage, null, c6);
        });

        String expectedMessage = "Maximum capacity exceeded!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldRemoveACargoFromShipmentIfItIsLoaded() {
        Cargo c1 = Cargo.builder()
                .id(1L)
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .id(2L)
                .capacity(new BigDecimal(3))
                .build();

        Cargo c3 = Cargo.builder()
                .id(3L)
                .capacity(new BigDecimal(2))
                .build();

        Cargo c4 = Cargo.builder()
                .id(4L)
                .capacity(new BigDecimal(3))
                .build();

        Cargo c5 = Cargo.builder()
                .id(5L)
                .capacity(new BigDecimal(6))
                .build();

        List<Cargo> cargo = new LinkedList<>(Arrays.asList(c1, c2, c3, c4, c5));


        Coordinate santosHarbour = Coordinate.builder()
                .x(-24.752646)
                .y(-47.572934)
                .build();

        Route route = Route.builder()
                .destination(santosHarbour)
                .arrival(LocalDateTime.now())
                .build();

        HashSet<Route> routes = new HashSet<>();
        routes.add(route);

        Voyage voyage = Voyage.builder()
                .cargo(cargo)
                .schedule(routes)
                .capacity(new BigDecimal(22))
                .build();

        Coordinate tubaraoHarbour = Coordinate.builder()
                .x(-28.467)
                .y(-49.0075)
                .build();

        Cargo unloadedCargo = this.voyageService.makeStepOverToUnload(voyage, tubaraoHarbour, c3);
        assertEquals(c3, unloadedCargo);
        assertEquals(4, voyage.getCargo().size());
    }

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

        Route santosToTubarao = Route.builder()
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

        Voyage savedVoyage = Voyage.builder()
                .id(1L)
                .capacity(shipCapacity)
                .destination(santaremToFelixStowe.getDestination())
                .origin(santosToTubarao.getOrigin())
                .schedule(schedule)
                .cargo(initialCargo)
                .build();

        when(this.voyageRepository.save(this.voyageArgumentCaptor.capture())).thenReturn(savedVoyage);
        Voyage voyage = this.voyageService.createShipment(10.0, schedule, initialCargo);

        assertEquals(2, this.voyageArgumentCaptor.getValue().getCargo().size());
        assertEquals(3, this.voyageArgumentCaptor.getValue().getSchedule().size());
        assertEquals(BigDecimal.valueOf(10.0), this.voyageArgumentCaptor.getValue().getCapacity());
        assertEquals(santosToTubarao.getOrigin(), this.voyageArgumentCaptor.getValue().getOrigin());
        assertEquals(santaremToFelixStowe.getDestination(), this.voyageArgumentCaptor.getValue().getDestination());
        assertEquals(1, voyage.getId());
    }
}