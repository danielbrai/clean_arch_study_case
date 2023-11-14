package br.com.danielbrai.clean_arch_study_case.route;

import br.com.danielbrai.clean_arch_study_case.enums.Operations;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class RouteRequestModel implements Serializable {

    private double xOrigin;
    private double yOrigin;
    private double xDestiny;
    private double yDestiny;
    private Operations operation;
}
