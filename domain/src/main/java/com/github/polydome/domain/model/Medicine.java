package com.github.polydome.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Medicine {
    private final int id;
    private final String ean;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final int packagingSize;
    private final String packagingUnit;
}
