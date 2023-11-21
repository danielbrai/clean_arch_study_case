package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.domain.Route;
import br.com.danielbrai.core.domain.Voyage;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@Named
public class RegisterDepartureUseCase {

    public void execute(Voyage voyage) {
        Route route = voyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();
        route.setDeparture(LocalDateTime.now());
    }
}
