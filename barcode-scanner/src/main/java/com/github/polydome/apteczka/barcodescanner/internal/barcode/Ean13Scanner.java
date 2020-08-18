package com.github.polydome.apteczka.barcodescanner.internal.barcode;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.EAN13Reader;

public class Ean13Scanner implements BarcodeScanner {
    EAN13Reader reader = new EAN13Reader();
    Binarizer binarizer;
    BinaryBitmap bitmap;

    @Override
    public String scanFrame(byte[] yuvFrame, int width, int height) {
        PlanarYUVLuminanceSource source =
                new PlanarYUVLuminanceSource(yuvFrame, width, height, 0, 0, width, height, false);

        binarizer = new HybridBinarizer(source);
        bitmap = new BinaryBitmap(binarizer);

        try {
            return reader.decode(bitmap).getText();
        } catch (NotFoundException e) {
            return null;
        } catch (FormatException e) {
            return null;
        }
    }
}
