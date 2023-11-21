package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.domain.Cargo;
import jakarta.inject.Named;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Named
public class DropExcessUseCase {

    public Cargo execute(List<Cargo> cargos, BigDecimal shipCapacity) {

        List<Cargo> orderedCargoByCapacity = cargos.stream().sorted(Comparator.comparing(Cargo::getCapacity)).toList();
        BigDecimal bookedCargo = cargos.stream().map(Cargo::getCapacity).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shippingOverbookingTax = bookedCargo.subtract(shipCapacity);

        int totalOfCargos = cargos.size();
        int start = 0;
        int end = cargos.size() - 1;
        Cargo target;

        if (shippingOverbookingTax.compareTo(orderedCargoByCapacity.get(start).getCapacity()) == 0) {
            target = cargos.get(start);
            cargos.remove(target);
        }

        if (shippingOverbookingTax.compareTo(orderedCargoByCapacity.get(end).getCapacity()) == 0) {
            target = cargos.get(end);
            cargos.remove(target);
        }

        int mid = 0;
        while (start < end) {
            mid = (start + end) / 2;

            BigDecimal actualCargoCapacity = orderedCargoByCapacity.get(mid).getCapacity();
            if (shippingOverbookingTax.compareTo(actualCargoCapacity) == 0) {
                target = orderedCargoByCapacity.get(mid);
                cargos.remove(target);
                return target;
            }

            if (shippingOverbookingTax.compareTo(actualCargoCapacity) < 0) {

                BigDecimal previousCargoCapacity = orderedCargoByCapacity.get(mid - 1).getCapacity();
                if (shippingOverbookingTax.compareTo(previousCargoCapacity) > 0) {
                    target = getClosestCargoToMoveToNextShip(orderedCargoByCapacity.get(mid - 1), orderedCargoByCapacity.get(mid), shippingOverbookingTax);
                    cargos.remove(target);
                    return target;
                }
                end = mid;
            }

            else {
                BigDecimal nextCargoCapacity = orderedCargoByCapacity.get(mid + 1).getCapacity();
                if (mid < totalOfCargos && shippingOverbookingTax.compareTo(nextCargoCapacity) < 0) {
                    target = getClosestCargoToMoveToNextShip(orderedCargoByCapacity.get(mid), orderedCargoByCapacity.get(mid + 1), shippingOverbookingTax);
                    cargos.remove(target);
                    return target;
                }
                start = mid + 1;
            }
        }

        target = cargos.get(mid);
        cargos.remove(target);
        return target;
    }

    private Cargo getClosestCargoToMoveToNextShip(Cargo cargo1, Cargo cargo2, BigDecimal target) {
        if (target.subtract(cargo1.getCapacity()).compareTo(cargo2.getCapacity().subtract(target)) > 0) {
            return cargo1;
        } else {
            return cargo2;
        }
    }
}
