package com.github.polydome.apteczka.domain.usecase.exception;

import java.util.Locale;

public class NoSuchMedicineException extends RuntimeException {
    public NoSuchMedicineException(long id) {
        super(String.format(Locale.getDefault(),
                "Medicine identified by [id=%d] does not exist", id));
    }
}
