package com.github.polydome.apteczka.domain.model;

import lombok.Data;

@Data
public class Product {
    private final String ean;
    private final String name;
    private final String commonName;
    private final String potency;
    private final String form;
    private final int packagingSize;
    private final String packagingUnit;
}
