package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Coordinate;
import br.com.danielbrai.core.entity.Voyage;
import br.com.danielbrai.core.exceptions.CargoNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RemoveCargoFromShipmentUseCaseTest {

    @InjectMocks
    RemoveCargoFromShipmentUseCase useCase;

    @Test
    void shouldThrownAnExceptionIfTheCargoWasNotLocated() {

        Exception exception = assertThrows(Exception.class, () -> {
            Coordinate loadLocation = Coordinate.builder()
                    .x(-24.752646)
                    .y(-47.572934)
                    .build();

            Cargo c1 = Cargo.builder()
                    .id(1L)
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c2 = Cargo.builder()
                    .id(2L)
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c3 = Cargo.builder()
                    .id(3L)
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c4 = Cargo.builder()
                    .id(4L)
                    .capacity(new BigDecimal(5))
                    .build();

            Cargo c5 = Cargo.builder()
                    .id(5L)
                    .capacity(new BigDecimal(1))
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

            Cargo execute = this.useCase.execute(voyage, 6L);

            assertNull(execute);
        });

        assertInstanceOf(CargoNotFoundException.class, exception);
        assertTrue(exception.getMessage().contains("The provided cargo isn't in this shipment."));
    }

    @Test
    void shouldRemoveTheInformedCargoWhenItsIdIsAmongTheShipmentCargo() {
        Coordinate loadLocation = Coordinate.builder()
                .x(-24.752646)
                .y(-47.572934)
                .build();

        Cargo c1 = Cargo.builder()
                .id(1L)
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .id(2L)
                .capacity(new BigDecimal(5))
                .build();

        Cargo c3 = Cargo.builder()
                .id(3L)
                .capacity(new BigDecimal(5))
                .build();

        Cargo c4 = Cargo.builder()
                .id(4L)
                .capacity(new BigDecimal(5))
                .build();

        Cargo c5 = Cargo.builder()
                .id(5L)
                .capacity(new BigDecimal(1))
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

        Cargo removedCargo = this.useCase.execute(voyage, 3L);

        assertEquals(c3, removedCargo);

    }
}