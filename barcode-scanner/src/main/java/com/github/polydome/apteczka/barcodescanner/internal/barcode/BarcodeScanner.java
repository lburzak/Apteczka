package com.github.polydome.apteczka.barcodescanner.internal.barcode;

public interface BarcodeScanner {
    String scanFrame(byte[] yuvFrame, int width, int height);
}
