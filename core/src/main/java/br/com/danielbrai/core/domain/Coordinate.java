package br.com.danielbrai.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coordinate {

    private Long id;

    private Double x;

    private Double y;
}
