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

    /**
     * Rotates frame by 90 degrees clockwise.
     *
     * Allows for proper barcode recognition when landscape camera is used on portrait orientation.
     * U and V bytes are filled with 0 to improve performance, as color is not relevant for
     * decoding.
     *
     * Note: Frame width and height will be swapped in resulting frame.
     *
     * @param frame YUV bytes
     * @param width Frame width
     * @param height Frame height
     * @return Rotated frame
     */
    byte[] rotateFrame(byte[] frame, int width, int height) {
        int pixels = width * height;
        byte[] rotatedFrame = new byte[pixels * 3/2];

        for (int i = 0; i < pixels; i++) {
            rotatedFrame[height - i / width + i % width * height] = frame[i];
        }

        for (int i = pixels; i < pixels * 3/2; i++) {
            rotatedFrame[i] = 0;
        }

        return rotatedFrame;
    }

    @Override
    public String scanFrame(byte[] yuvFrame, int width, int height) {
        yuvFrame = rotateFrame(yuvFrame, width, height);

        // `height` and `width` arguments are swapped, because the frame has been rotated
        // noinspection SuspiciousNameCombination
        PlanarYUVLuminanceSource source =
                new PlanarYUVLuminanceSource(yuvFrame, height, width, 0, 0, height, width, false);

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
