package br.com.danielbrai.core.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Cargo {

    private Long id;

    private BigDecimal capacity;

    private Coordinate loadLocation;

    private Coordinate unloadLocation;
}
