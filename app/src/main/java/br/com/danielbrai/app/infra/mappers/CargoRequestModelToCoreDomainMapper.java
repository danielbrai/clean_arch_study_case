package br.com.danielbrai.app.infra.mappers;

import br.com.danielbrai.app.entrypoint.rest.model.request.CargoRequestModel;
import br.com.danielbrai.core.domain.Cargo;
import jakarta.inject.Named;

import java.math.BigDecimal;

@Named
public class CargoRequestModelToCoreDomainMapper implements Mapper<CargoRequestModel, Cargo> {
    @Override
    public Cargo map(CargoRequestModel input) {
        return Cargo.builder()
                .capacity(BigDecimal.valueOf(input.getCapacity()))
                .build();
    }
}
