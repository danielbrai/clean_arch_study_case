package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.domain.Cargo;
import br.com.danielbrai.core.domain.Coordinate;
import br.com.danielbrai.core.domain.Route;
import br.com.danielbrai.core.domain.Voyage;
import br.com.danielbrai.core.domain.enums.Operations;
import br.com.danielbrai.core.exceptions.InvalidArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;

@ExtendWith(MockitoExtension.class)
class PrepareShipmentToDepartureTest {

    @InjectMocks
    private PrepareShipmentToDeparture useCase;

    @Mock
    private AddCargoToShipmentUseCase addCargoToShipmentUseCase;

    @Mock
    private RegisterDepartureUseCase registerDepartureUseCase;

    @Test
    void shouldCreateTheShipmentPointTheFirstRouteAsSource() {

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
        LinkedHashSet<Route> schedule = new LinkedHashSet<>();
        schedule.add(santosToTubarao);
        schedule.add(tubaraoToSantarem);
        schedule.add(santaremToFelixStowe);

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();
        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();
        LinkedList<Cargo> initialCargo = new LinkedList<>();
        initialCargo.add(c1);
        initialCargo.add(c2);

        doCallRealMethod().when(this.addCargoToShipmentUseCase).execute(any(), any(), any());

        Voyage execute = this.useCase.execute(12, schedule, initialCargo);

        assertEquals(2, execute.getCargo().size());
        assertEquals(santosPort, execute.getCargo().get(0).getLoadLocation());
        assertEquals(santosPort, execute.getCargo().get(1).getLoadLocation());
    }

    @Test
    void shouldThrowsAnExceptionIfTheScheduleIsEmpty() {

        LinkedHashSet<Route> schedule = new LinkedHashSet<>();

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();
        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();
        LinkedList<Cargo> initialCargo = new LinkedList<>();
        initialCargo.add(c1);
        initialCargo.add(c2);

        Exception exception = assertThrows(Exception.class, () -> {
            Voyage execute = this.useCase.execute(12, schedule, initialCargo);
        });

        assertInstanceOf(InvalidArgumentException.class, exception);
        assertTrue(exception.getMessage().contains("The schedule must have at least two routes!"));
    }

    @Test
    void shouldThrowsAnExceptionIfTheScheduleHasOnlyOneRoute() {

        Coordinate santosPort = Coordinate.builder()
                .x(-24.752646)
                .y(-47.572934)
                .build();
        Coordinate tubaraoPort = Coordinate.builder()
                .x(-28.467)
                .y(-49.0075)
                .build();
        Route santosToTubarao = Route.builder()
                .operation(Operations.LOAD)
                .origin(santosPort)
                .destination(tubaraoPort)
                .build();
        LinkedHashSet<Route> schedule = new LinkedHashSet<>();
        schedule.add(santosToTubarao);

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();
        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();
        LinkedList<Cargo> initialCargo = new LinkedList<>();
        initialCargo.add(c1);
        initialCargo.add(c2);

        Exception exception = assertThrows(Exception.class, () -> {
            Voyage execute = this.useCase.execute(12, schedule, initialCargo);
        });

        assertInstanceOf(InvalidArgumentException.class, exception);
        assertTrue(exception.getMessage().contains("The schedule must have at least two routes!"));
    }

    @Test
    void shouldThrowsAnExceptionIfTheScheduleIsNull() {


        LinkedHashSet<Route> schedule = null;

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();
        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();
        LinkedList<Cargo> initialCargo = new LinkedList<>();
        initialCargo.add(c1);
        initialCargo.add(c2);

        Exception exception = assertThrows(Exception.class, () -> {
            Voyage execute = this.useCase.execute(12, schedule, initialCargo);
        });

        assertInstanceOf(InvalidArgumentException.class, exception);
        assertTrue(exception.getMessage().contains("The schedule must have at least two routes!"));
    }

    @Test
    void shouldThrowsAnExceptionIfTheInitialCargoIsEmpty() {

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
        LinkedHashSet<Route> schedule = new LinkedHashSet<>();
        schedule.add(santosToTubarao);
        schedule.add(tubaraoToSantarem);
        schedule.add(santaremToFelixStowe);

        LinkedList<Cargo> initialCargo = new LinkedList<>();

        Exception exception = assertThrows(Exception.class, () -> {
            Voyage execute = this.useCase.execute(12, schedule, initialCargo);
        });

        assertInstanceOf(InvalidArgumentException.class, exception);
        assertTrue(exception.getMessage().contains("The initial cargo must have at least one cargo!"));
    }

    @Test
    void shouldThrowsAnExceptionIfTheInitialCargoIsNull() {


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
        LinkedHashSet<Route> schedule = new LinkedHashSet<>();
        schedule.add(santosToTubarao);
        schedule.add(tubaraoToSantarem);
        schedule.add(santaremToFelixStowe);

        LinkedList<Cargo> initialCargo = null;

        Exception exception = assertThrows(Exception.class, () -> {
            Voyage execute = this.useCase.execute(12, schedule, initialCargo);
        });

        assertInstanceOf(InvalidArgumentException.class, exception);
        assertTrue(exception.getMessage().contains("The initial cargo must have at least one cargo!"));
    }

}