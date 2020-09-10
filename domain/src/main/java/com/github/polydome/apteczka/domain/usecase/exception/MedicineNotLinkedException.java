package com.github.polydome.apteczka.domain.usecase.exception;

import java.util.Locale;

/**
 * Thrown when medicine has no product linked to it
 */
public class MedicineNotLinkedException extends RuntimeException {
    public MedicineNotLinkedException(long id) {
        super(String.format(Locale.getDefault(),
                "Medicine identified by [id=%d] has no product linked to it", id));
    }
}
