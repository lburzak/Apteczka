package com.github.polydome.apteczka.barcodescanner;

import io.reactivex.Maybe;

public interface BarcodeScanner {
    /**
     * Performs scanning for EAN-13 or EAN-8 barcode
     * @return Source emitting the code when it's found, or completing empty if cancelled
     */
    Maybe<String> scanEan();
}
