package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class VoyageService {

    public static final double OVERBOOKING_TAX = 1.1;

    private final VoyageRepository voyageRepository;

    private final VoyageRequestModelToEntityMapper voyageRequestModelToEntityMapper;

    public Voyage createShipment(VoyageRequestModel voyageRequestModel) {

        Voyage mappedVoyage = this.voyageRequestModelToEntityMapper.map(voyageRequestModel);

        Route origin = mappedVoyage.getSchedule().stream().findFirst().orElseThrow();
        Route destination = mappedVoyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();

        Voyage voyage = Voyage.builder()
                .origin(origin.getOrigin())
                .destination(destination.getDestination())
                .capacity(mappedVoyage.getCapacity())
                .schedule(mappedVoyage.getSchedule())
                .cargo(new LinkedList<>())
                .build();

        mappedVoyage.getCargo().forEach(cargo -> this.addCargoToShipment(voyage, origin.getOrigin(), cargo));
        this.registerDeparture(voyage);
        return this.voyageRepository.save(voyage);
    }

    private void addCargoToShipment(Voyage voyage, Coordinate loadLocation, Cargo cargo) {

        BigDecimal bookedCargo = voyage.getCargo().stream().map(Cargo::getCapacity).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal maximumCapacity = voyage.getCapacity().multiply(BigDecimal.valueOf(OVERBOOKING_TAX));

        if (bookedCargo.add(cargo.getCapacity()).compareTo(maximumCapacity) > 0) {
            throw new RuntimeException("Maximum capacity exceeded!");
        }

        cargo.setLoadLocation(loadLocation);
        cargo.setUnloadLocation(loadLocation);
        voyage.addCargo(cargo);
    }

    private Cargo removeCargoFromShipment(Voyage voyage, Long idCargo) {

        Cargo cargoToUnload = voyage.getCargo().stream().filter(c -> Objects.equals(c.getId(), idCargo)).findFirst().orElse(null);
        voyage.getCargo().remove(cargoToUnload);
        return cargoToUnload;
    }

    public void makeStepOverToLoad(Voyage voyage, Coordinate destination, Cargo cargo) {
        this.addCargoToShipment(voyage, destination, cargo);
        this.makeWarehouseOperation(voyage, Operations.LOAD, destination);
        this.registerArrival(voyage);
    }

    public Cargo makeStepOverToUnload(Voyage voyage, Coordinate destination, Cargo cargo) {
        this.makeWarehouseOperation(voyage, Operations.UNLOAD, destination);
        this.registerArrival(voyage);
        return this.removeCargoFromShipment(voyage, cargo.getId());
    }

    private void makeWarehouseOperation(Voyage voyage, Operations operation, Coordinate destination) {
        Route partialRoute = Route.builder()
                .operation(operation)
                .destination(destination)
                .build();
        voyage.getSchedule().add(partialRoute);
    }

    public Cargo dropExcess(List<Cargo> cargos, BigDecimal shipCapacity) {

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

    public void registerArrival(Voyage voyage) {
        Route route = voyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();
        route.setArrival(LocalDateTime.now());
    }

    public void registerDeparture(Voyage voyage) {
        Route route = voyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();
        route.setDeparture(LocalDateTime.now());
    }

    public Voyage retriveVoyage(Long id) {
        return this.voyageRepository.findById(id).orElse(null);
    }
}
