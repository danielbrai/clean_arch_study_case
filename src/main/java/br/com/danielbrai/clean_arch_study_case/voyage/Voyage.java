package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.Cargo;
import br.com.danielbrai.clean_arch_study_case.route.Route;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class Voyage {

    private Route source;
    private Route destination;
    private List<Cargo> cargo;
    private Set<Route> schedule;
    private BigDecimal capacity;
}
