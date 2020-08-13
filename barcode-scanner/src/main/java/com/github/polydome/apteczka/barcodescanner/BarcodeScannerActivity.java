package com.github.polydome.apteczka.barcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.polydome.apteczka.barcodescanner.internal.barcode.BarcodeScanner;
import com.github.polydome.apteczka.barcodescanner.internal.barcode.Ean13Scanner;
import com.github.polydome.apteczka.barcodescanner.internal.android.AndroidCameraPreview;
import com.github.polydome.apteczka.barcodescanner.internal.camera.PreviewListener;

import io.reactivex.disposables.CompositeDisposable;

public class BarcodeScannerActivity extends AppCompatActivity implements PreviewListener {
    public final static String INPUT_EXTRA_BARCODE_TYPE = "com.github.polydome.apteczka.barcodescanner.BarcodeType";
    public final static String OUTPUT_EXTRA_MISSING_PERMISSIONS = "com.github.polydome.apteczka.barcodescanner.MissingPermissions";
    public final static String OUTPUT_EXTRA_EXCEPTION = "com.github.polydome.apteczka.barcodescanner.Exception";
    public final static String OUTPUT_EXTRA_CODE = "com.github.polydome.apteczka.barcodescanner.Code";
    public final static int RESULT_CODE_SUCCESS = RESULT_OK;
    public final static int RESULT_CODE_INSUFFICIENT_PERMISSIONS = 1;
    public final static int RESULT_CODE_UNEXPECTED_EXCEPTION = 2;

    private Camera camera;
    private FrameLayout previewHolder;
    private BarcodeScanner scanner;

    private final CompositeDisposable comp = new CompositeDisposable();

    private String scanResult;

    public BarcodeScannerActivity() {
        super(R.layout.activity_barcode_scanner);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isCameraPermitted())
            finishInsufficientPermissions();

        int barcodeTypeIndex = getIntent().getIntExtra(INPUT_EXTRA_BARCODE_TYPE, 0);

        switch (BarcodeType.values()[barcodeTypeIndex]) {
            case EAN_13:
                this.scanner = new Ean13Scanner();
                break;
            default:
                throw new UnsupportedOperationException("Scanner for barcode type [" + BarcodeType.values()[barcodeTypeIndex] + "] not available");
        }

        previewHolder = findViewById(R.id.barcodeScanner_previewHolder);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isCameraPermitted()) {
            showPreview();
        } else {
            finishInsufficientPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        comp.dispose();
    }

    private void showPreview() {
        try {
            camera = Camera.open();
        } catch (Exception e) {
            finishUnexpectedException(e);
        }

        AndroidCameraPreview preview = new AndroidCameraPreview(this, camera);
        preview.setListener(this);

        previewHolder.addView(preview);
    }

    private boolean isCameraPermitted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void finishInsufficientPermissions() {
        Intent intent = new Intent();

        intent.putExtra(
                OUTPUT_EXTRA_MISSING_PERMISSIONS,
                new String[]{ Manifest.permission.CAMERA }
        );

        setResult(RESULT_CODE_INSUFFICIENT_PERMISSIONS, intent);
        finish();
    }

    private void finishUnexpectedException(Exception e) {
        Intent intent = new Intent();
        intent.putExtra(OUTPUT_EXTRA_EXCEPTION, e);

        setResult(RESULT_CODE_UNEXPECTED_EXCEPTION, intent);
        finish();
    }

    private void finishSuccess(String code) {
        Intent intent = new Intent();
        intent.putExtra(OUTPUT_EXTRA_CODE, code);

        setResult(RESULT_CODE_SUCCESS, intent);
        finish();
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        scanResult = scanner.scanFrame(
                data,
                camera.getParameters().getPreviewSize().width,
                camera.getParameters().getPreviewSize().height
        );

        if (scanResult != null) {
            finishSuccess(scanResult);
        }
    }

    @Override
    public void onPreviewException(Exception exception) {
        finishUnexpectedException(exception);
    }
}
