package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.EditMedicineContract;
import com.github.polydome.apteczka.view.ui.common.BaseActivity;

import javax.inject.Inject;

public class EditMedicineActivity extends BaseActivity implements EditMedicineContract.View {
    @Inject
    public EditMedicineContract.Presenter presenter;

    private EditText eanField;
    private EditText nameField;
    private EditText commonNameField;
    private EditText potencyField;
    private EditText formField;
    private EditText packagingSizeField;
    private EditText packagingUnitField;

    public EditMedicineActivity() {
        super(R.layout.activity_edit_medicine);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresentationComponent().inject(this);
        presenter.attach(this);

        eanField = findViewById(R.id.editMedicine_ean);
        nameField = findViewById(R.id.editMedicine_name);
        commonNameField = findViewById(R.id.editMedicine_commonName);
        potencyField = findViewById(R.id.editMedicine_potency);
        formField = findViewById(R.id.editMedicine_form);
        packagingSizeField = findViewById(R.id.editMedicine_packagingSize);
        packagingUnitField = findViewById(R.id.editMedicine_packagingUnit);

        long medicineId = getIntent().getLongExtra("MEDICINE_ID", 0);
        presenter.onCurrentDataRequest(medicineId);
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
}
