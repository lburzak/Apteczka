package com.github.polydome.apteczka.barcodescanner.internal.camera;

import android.hardware.Camera;

public interface PreviewListener extends Camera.PreviewCallback {
    void onPreviewException(Exception exception);
}
