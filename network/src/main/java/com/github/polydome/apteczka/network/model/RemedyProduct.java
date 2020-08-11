package com.github.polydome.apteczka.network.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemedyProduct {
    private final int id;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final String activeSubstance;
    private final String atc;
}
