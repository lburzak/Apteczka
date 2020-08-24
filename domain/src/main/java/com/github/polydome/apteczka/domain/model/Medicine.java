package com.github.polydome.apteczka.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Medicine {
    private final long id;
    private final String title;
}
