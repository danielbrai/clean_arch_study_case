package br.com.danielbrai.core.entity;

import br.com.danielbrai.core.entity.enums.Operations;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class Route {

    private Long id;

    private Coordinate origin;

    private Coordinate destination;

    private LocalDateTime departure;

    private LocalDateTime arrival;

    private Operations operation;
}
