package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.domain.Route;
import br.com.danielbrai.core.domain.Voyage;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@Named
public class RegisterArrivalUseCase {

    public void registerArrival(Voyage voyage) {
        Route route = voyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();
        route.setArrival(LocalDateTime.now());
    }
}
