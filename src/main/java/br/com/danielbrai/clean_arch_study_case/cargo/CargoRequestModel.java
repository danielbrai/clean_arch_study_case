package br.com.danielbrai.clean_arch_study_case.cargo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CargoRequestModel implements Serializable {

    private double xOrigin;
    private double yOrigin;
    private double xDestiny;
    private double yDestiny;
    private double capacity;
}
