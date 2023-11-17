package br.com.danielbrai.core.entity;

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
