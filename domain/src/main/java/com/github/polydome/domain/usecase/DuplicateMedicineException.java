package com.github.polydome.domain.usecase;

public class DuplicateMedicineException extends RuntimeException {
    public DuplicateMedicineException(String ean) {
        super(String.format("Medicine identified with EAN %s already exists", ean));
    }
}
