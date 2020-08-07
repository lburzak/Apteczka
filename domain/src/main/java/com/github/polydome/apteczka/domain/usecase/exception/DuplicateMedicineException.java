package com.github.polydome.apteczka.domain.usecase.exception;

public class DuplicateMedicineException extends RuntimeException {
    public DuplicateMedicineException(String ean) {
        super(String.format("Medicine identified with EAN %s already exists", ean));
    }
}
