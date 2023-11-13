package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VoyageServiceTest {

    @InjectMocks VoyageService voyageService;

    @Test
    void dropExcess() {

        Cargo c1 = Cargo.builder()
                .capacity(new BigDecimal(5))
                .build();

        Cargo c2 = Cargo.builder()
                .capacity(new BigDecimal(2))
                .build();

        Cargo c3 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        Cargo c4 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c5 = Cargo.builder()
                .capacity(new BigDecimal(6))
                .build();

        Cargo c6 = Cargo.builder()
                .capacity(new BigDecimal(3))
                .build();

        List<Cargo> inputCargo = Arrays.asList(c1, c2, c3, c4, c5, c6);

        Cargo cargo = this.voyageService.dropExcess(inputCargo, BigDecimal.valueOf(22));

        assertEquals(c2, cargo);

    }

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
}