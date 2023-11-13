package br.com.danielbrai.clean_arch_study_case.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Priorities {

    LOW(1), MEDIUM(2), HIGH(3);

    private int value;
}
