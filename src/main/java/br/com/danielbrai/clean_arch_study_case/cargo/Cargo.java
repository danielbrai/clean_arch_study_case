package br.com.danielbrai.clean_arch_study_case.cargo;

import br.com.danielbrai.clean_arch_study_case.enums.Priorities;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Cargo {

    private BigDecimal capacity;
    private Priorities priority;
}
