package com.github.polydome.apteczka.network.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemedyPackaging {
    private final long id;
    private final int productId;
    private final int size;
    private final String unit;
    private final String ean;
}
