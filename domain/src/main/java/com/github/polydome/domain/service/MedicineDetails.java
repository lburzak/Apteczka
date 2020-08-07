package com.github.polydome.domain.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicineDetails {
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final int packagingSize;
    private final String packagingUnit;
}
