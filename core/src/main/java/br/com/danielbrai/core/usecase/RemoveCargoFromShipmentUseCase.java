package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.entity.Cargo;
import br.com.danielbrai.core.entity.Voyage;
import br.com.danielbrai.core.exceptions.CargoNotFoundException;

import javax.inject.Named;
import java.util.Objects;

@Named
public class RemoveCargoFromShipmentUseCase {

    public Cargo execute(Voyage voyage, Long idCargo) {

        Cargo cargoToUnload = voyage.getCargo().stream().filter(c -> Objects.equals(c.getId(), idCargo)).findFirst().orElseThrow(() -> new CargoNotFoundException("The provided cargo isn't in this shipment."));
        voyage.getCargo().remove(cargoToUnload);
        return cargoToUnload;
    }
}
