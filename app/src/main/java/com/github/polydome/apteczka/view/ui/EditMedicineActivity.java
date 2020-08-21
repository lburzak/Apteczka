package com.github.polydome.apteczka.view.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.barcodescanner.BarcodeScannerActivity;
import com.github.polydome.apteczka.barcodescanner.BarcodeScannerOptions;
import com.github.polydome.apteczka.barcodescanner.BarcodeType;
import com.github.polydome.apteczka.view.contract.EditMedicineContract;
import com.github.polydome.apteczka.view.ui.common.PresentationComponentProvider;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

public class EditMedicineActivity extends AppCompatActivity implements EditMedicineContract.View, Toolbar.OnMenuItemClickListener {
    private final static int BARCODE_SCAN_REQUEST_CODE = 3691;
    private final static int BARCODE_SCAN_PERMISSIONS_REQUEST_CODE = 4921;
    private final static int EAN13_LENGTH = 13;

    private PresentationComponentProvider componentProvider;

    @Inject
    public EditMedicineContract.Presenter presenter;

    private long medicineId;

    private EditText eanField;
    private EditText nameField;
    private EditText commonNameField;
    private EditText potencyField;
    private EditText formField;
    private EditText packagingSizeField;
    private EditText packagingUnitField;
    private FloatingActionButton fab;

    public EditMedicineActivity() {
        super(R.layout.activity_edit_medicine);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.componentProvider = new PresentationComponentProvider(this);

        componentProvider.getPresentationComponent().inject(this);
        presenter.attach(this);

        eanField = findViewById(R.id.editMedicine_ean);
        nameField = findViewById(R.id.editMedicine_name);
        commonNameField = findViewById(R.id.editMedicine_commonName);
        potencyField = findViewById(R.id.editMedicine_potency);
        formField = findViewById(R.id.editMedicine_form);
        packagingSizeField = findViewById(R.id.editMedicine_packagingSize);
        packagingUnitField = findViewById(R.id.editMedicine_packagingUnit);
        fab = findViewById(R.id.editMedicine_fab);

        medicineId = getIntent().getLongExtra("MEDICINE_ID", 0);
        if (medicineId > 0)
            presenter.onCurrentDataRequest(medicineId);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomAppBar.setOnMenuItemClickListener(this);

        eanField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == EAN13_LENGTH) {
                    presenter.onEanInput(s.toString());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fab.setOnClickListener(view -> {
            saveMedicine();
            finish();
        });
    }

    @Override
    public boolean onMenuItemClick(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editMedicine_action_scan:
                startEanScanner();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showEan(String ean) {
        eanField.setText(ean);
    }

    @Override
    public void showName(String name) {
        nameField.setText(name);
    }

    @Override
    public void showCommonName(String commonName) {
        commonNameField.setText(commonName);
    }

    @Override
    public void showPotency(String potency) {
        potencyField.setText(potency);
    }

    @Override
    public void showForm(String form) {
        formField.setText(form);
    }

    @Override
    public void showPackagingSize(int size) {
        packagingSizeField.setText(String.valueOf(size));
    }

    @Override
    public void showPackagingUnit(String unit) {
        packagingUnitField.setText(unit);
    }

    private void startEanScanner() {
        Intent scannerIntent = new Intent(this, BarcodeScannerActivity.class);
        Bundle scannerOptions = new BarcodeScannerOptions()
                .type(BarcodeType.EAN_13)
                .bundle();

        startActivityForResult(scannerIntent, BARCODE_SCAN_REQUEST_CODE, scannerOptions);
    }

    private void saveMedicine() {
        presenter.onSaveMedicine(
                medicineId,
                eanField.getText().toString(),
                nameField.getText().toString(),
                commonNameField.getText().toString(),
                potencyField.getText().toString(),
                formField.getText().toString(),
                Integer.parseInt(packagingSizeField.getText().toString()),
                packagingUnitField.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BARCODE_SCAN_REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_CANCELED:
                    break;
                case BarcodeScannerActivity.RESULT_CODE_SUCCESS:
                    eanField.setText(data.getStringExtra(BarcodeScannerActivity.OUTPUT_EXTRA_CODE));
                    break;
                case BarcodeScannerActivity.RESULT_CODE_INSUFFICIENT_PERMISSIONS:
                    if (deviceRequiresExplicitPermissions()) {
                        requestBarcodeScannerPermissions(
                                data.getStringArrayExtra(BarcodeScannerActivity.OUTPUT_EXTRA_MISSING_PERMISSIONS)
                        );
                    } else {
                        throw new UnsupportedOperationException("Unexpected result code: " + resultCode);
                    }
                    break;
                case BarcodeScannerActivity.RESULT_CODE_UNEXPECTED_EXCEPTION:
                    Exception exception = (Exception) data.getSerializableExtra(BarcodeScannerActivity.OUTPUT_EXTRA_EXCEPTION);
                    Log.e("EditMedicineActivity",
                            "Barcode scanner encountered an unexpected exception: " + exception.getMessage());
                    break;
                default:
                    throw new UnsupportedOperationException("Unexpected result code: " + resultCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == BARCODE_SCAN_PERMISSIONS_REQUEST_CODE) {
            for (final int result : grantResults)
                if (result == PackageManager.PERMISSION_DENIED)
                    return;

            startEanScanner();
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private void requestBarcodeScannerPermissions(String[] permissions) {
        requestPermissions(permissions, BARCODE_SCAN_PERMISSIONS_REQUEST_CODE);
    }

    private boolean deviceRequiresExplicitPermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
