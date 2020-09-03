package com.github.polydome.apteczka.domain.usecase.exception;

import java.util.Locale;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String ean) {
        super(String.format(Locale.getDefault(),
                "Product identified by [ean=%s] is not available", ean));
    }
}
