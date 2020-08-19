package com.github.polydome.apteczka.barcodescanner.internal.android;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.polydome.apteczka.barcodescanner.internal.camera.CameraPreview;
import com.github.polydome.apteczka.barcodescanner.internal.camera.PreviewListener;

import java.io.IOException;

public class AndroidCameraPreview extends SurfaceView implements SurfaceHolder.Callback, CameraPreview {
    private final SurfaceHolder holder;
    private final Camera camera;
    private PreviewListener listener;

    public void setListener(PreviewListener listener) {
        this.listener = listener;
    }

    public AndroidCameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;

        holder = getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            startPreview();
        } catch (IOException e) {
            listener.onPreviewException(e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
    }

    private void startPreview() throws IOException {
        Camera.Parameters params = camera.getParameters();
        params.setPreviewFormat(ImageFormat.YV12);
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);

        camera.setParameters(params);
        camera.setDisplayOrientation(90);

        camera.setPreviewDisplay(holder);

        camera.setPreviewCallback(listener);
        camera.startPreview();
    }
}
