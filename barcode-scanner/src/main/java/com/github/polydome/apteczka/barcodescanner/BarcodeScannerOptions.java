package com.github.polydome.apteczka.barcodescanner;

import android.os.Bundle;

public class BarcodeScannerOptions {
    private final Bundle bundle = new Bundle();

    public BarcodeScannerOptions() {}

    public BarcodeScannerOptions type(BarcodeType type) {
        bundle.putInt(BarcodeScannerActivity.INPUT_EXTRA_BARCODE_TYPE, type.ordinal());
        return this;
    }

    public Bundle bundle() {
        return this.bundle;
    }
}
