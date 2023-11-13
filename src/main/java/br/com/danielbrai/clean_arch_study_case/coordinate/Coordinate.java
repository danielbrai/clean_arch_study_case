package br.com.danielbrai.clean_arch_study_case.coordinate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinate {

    private double x;
    private double y;
}
