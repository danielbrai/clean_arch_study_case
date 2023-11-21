package br.com.danielbrai.core.usecase;


import br.com.danielbrai.core.domain.Cargo;
import br.com.danielbrai.core.domain.Coordinate;
import br.com.danielbrai.core.domain.Route;
import br.com.danielbrai.core.domain.Voyage;
import br.com.danielbrai.core.exceptions.MaximumCapacityExceededException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddCargoToShipmentUseCaseTest {

    @InjectMocks
    private AddCargoToShipmentUseCase useCase;

    @Test
    void shouldThrownAnExceptionWhenMaximumCapacityIsExceeded(){

        Exception exceptionThrowed = assertThrows(Exception.class, () -> {

            Coordinate loadLocation = Coordinate.builder()
                    .x(-24.752646)
                    .y(-47.572934)
                    .build();

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

            LinkedList<Cargo> cargos = new LinkedList<>();
            cargos.add(c1);
            cargos.add(c2);
            cargos.add(c3);
            cargos.add(c4);
            cargos.add(c5);

            Voyage voyage = Voyage.builder()
                    .cargo(cargos)
                    .capacity(BigDecimal.valueOf(21))
                    .build();

            this.useCase.execute(voyage, loadLocation, c6);

        });

        assertTrue(exceptionThrowed instanceof MaximumCapacityExceededException);

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

        this.useCase.execute(voyage, null, c6);

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

        this.useCase.execute(voyage, null, c6);

        assertEquals(6, voyage.getCargo().size());

    }

    @Test
    void shouldnotAcceptMoreCargoIfShipmentIsFullyLoaded() {

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

        Exception exception = assertThrows(RuntimeException.class, () -> this.useCase.execute(voyage, null, c6));

        String expectedMessage = "Maximum capacity exceeded!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}