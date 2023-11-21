package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.domain.Cargo;
import br.com.danielbrai.core.domain.Coordinate;
import br.com.danielbrai.core.domain.Voyage;
import br.com.danielbrai.core.exceptions.MaximumCapacityExceededException;
import jakarta.inject.Named;

import java.math.BigDecimal;

@Named
public class AddCargoToShipmentUseCase {

    private static final double OVERBOOKING_TAX = 1.1;

    public void execute(Voyage voyage, Coordinate loadLocation, Cargo cargo) {
        BigDecimal bookedCargo = voyage.getCargo().stream().map(Cargo::getCapacity).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal maximumCapacity = voyage.getCapacity().multiply(BigDecimal.valueOf(OVERBOOKING_TAX));

        if (bookedCargo.add(cargo.getCapacity()).compareTo(maximumCapacity) > 0) {
            throw new MaximumCapacityExceededException("Maximum capacity exceeded!");
        }

        cargo.setLoadLocation(loadLocation);
        voyage.addCargo(cargo);
    }
}
