package br.com.danielbrai.clean_arch_study_case.route;

import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import br.com.danielbrai.clean_arch_study_case.coordinate.Coordinate;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Route {
    private final Coordinate from;
    private final Coordinate to;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private final Operations operation;
}
