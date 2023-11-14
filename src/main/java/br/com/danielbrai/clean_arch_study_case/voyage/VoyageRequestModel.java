package br.com.danielbrai.clean_arch_study_case.voyage;

import br.com.danielbrai.clean_arch_study_case.cargo.CargoRequestModel;
import br.com.danielbrai.clean_arch_study_case.route.RouteRequestModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class VoyageRequestModel implements Serializable {

    private Set<RouteRequestModel> schedule;
    private List<CargoRequestModel> cargo;
    private double capacity;
}
