package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.location.Location;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

@Service
public class VoyageService {

    public Voyage createVoyage(Location source, Location destination, BigDecimal shipCapacity) {

        Route sourceRoute = source.getRoute();

        Voyage voyage = Voyage.builder()
                .source(source)
                .destination(destination)
                .capacity(shipCapacity)
                .route(new HashSet<>())
                .build();

        voyage.getRoute().add(sourceRoute);

        return voyage;
    }

    public void addCargo(Voyage voyage, Cargo cargo) {

        BigDecimal bookedCargo = voyage.getCargo().stream().map(Cargo::getCapacity).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal maximumCapacity = voyage.getCapacity().multiply(BigDecimal.valueOf(1.1));

        if (bookedCargo.add(cargo.getCapacity()).compareTo(maximumCapacity) > 0) {
            throw new RuntimeException("Maximum capacity exceeded!");
        }

        voyage.getCargo().add(cargo);
    }

    public Cargo dropExcess(List<Cargo> cargos, BigDecimal shipCapacity) {

        List<Cargo> orderedCargoByCapacity = cargos.stream().sorted(Comparator.comparing(Cargo::getCapacity)).toList();
        BigDecimal bookedCargo = cargos.stream().map(Cargo::getCapacity).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shippingOverbookingTax = bookedCargo.subtract(shipCapacity);

        int totalOfCargos = cargos.size();
        int start = 0;
        int end = cargos.size() - 1;
        Cargo target = null;


        if (shippingOverbookingTax.compareTo(cargos.get(start).getCapacity()) == 0) {
            target = cargos.get(start);
            cargos.remove(target);
        }

        if (shippingOverbookingTax.compareTo(cargos.get(end).getCapacity()) == 0) {
            target = cargos.get(end);
            cargos.remove(target);
        }

        int mid = 0;
        while (start < end) {
            mid = (start + end) / 2;

            BigDecimal actualCargoCapacity = cargos.get(mid).getCapacity();
            if (shippingOverbookingTax.compareTo(actualCargoCapacity) == 0) {
                target = cargos.get(mid);
                cargos.remove(target);
            }

            if (shippingOverbookingTax.compareTo(actualCargoCapacity) < 0) {

                BigDecimal previousCargoCapacity = cargos.get(mid - 1).getCapacity();
                if (mid > 0 && shippingOverbookingTax.compareTo(previousCargoCapacity) > 0) {
                    target = getClosest(cargos.get(mid - 1), cargos.get(mid), shippingOverbookingTax);
                    cargos.remove(target);
                    return target;
                }
                end = mid;
            }

            else {
                BigDecimal nextCargoCapacity = cargos.get(mid + 1).getCapacity();
                if (mid < totalOfCargos && shippingOverbookingTax.compareTo(nextCargoCapacity) < 0) {
                    target = getClosest(cargos.get(mid), cargos.get(mid + 1), shippingOverbookingTax);
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

    private Cargo getClosest(Cargo cargo1, Cargo cargo2, BigDecimal target) {
        if (target.subtract(cargo1.getCapacity()).compareTo(cargo2.getCapacity().subtract(target)) > 0) {
            return cargo1;
        } else {
            return cargo2;
        }
    }
}