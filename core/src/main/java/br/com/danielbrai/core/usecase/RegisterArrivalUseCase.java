package br.com.danielbrai.core.usecase;

import br.com.danielbrai.core.entity.Route;
import br.com.danielbrai.core.entity.Voyage;

import javax.inject.Named;
import java.time.LocalDateTime;

@Named
public class RegisterArrivalUseCase {

    public void registerArrival(Voyage voyage) {
        Route route = voyage.getSchedule().stream().reduce((a, b) -> b).orElseThrow();
        route.setArrival(LocalDateTime.now());
    }
}
