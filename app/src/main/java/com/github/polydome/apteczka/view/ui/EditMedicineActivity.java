package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
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

    public EditMedicineActivity() {
        super(R.layout.activity_edit_medicine);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPresentationComponent().inject(this);
        presenter.attach(this);

        eanField = findViewById(R.id.editMedicine_ean);

        long medicineId = getIntent().getLongExtra("MEDICINE_ID", 0);
        presenter.onCurrentDataRequest(medicineId);
    }

    @Override
    public void showEan(String ean) {
        eanField.setText(ean);
    }
}
