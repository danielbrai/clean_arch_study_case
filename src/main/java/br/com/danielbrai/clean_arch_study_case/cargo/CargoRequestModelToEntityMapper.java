package br.com.danielbrai.clean_arch_study_case.cargo;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CargoRequestModelToEntityMapper {

    public Cargo map(CargoRequestModel requestModel) {

        return Cargo.builder()
                .capacity(BigDecimal.valueOf(requestModel.getCapacity()))
                .build();
    }
}
