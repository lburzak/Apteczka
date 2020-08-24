package com.github.polydome.apteczka.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Product {
    private final long id;
    private final String ean;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final int packagingSize;
    private final String packagingUnit;
}
