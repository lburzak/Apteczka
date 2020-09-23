package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.EditMedicineContract;
import com.github.polydome.apteczka.view.ui.common.PresentationComponentProvider;
import com.github.polydome.apteczka.view.ui.medicineeditor.MedicineEditorViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

public class EditMedicineActivity extends AppCompatActivity implements EditMedicineContract.View, Toolbar.OnMenuItemClickListener {
    private PresentationComponentProvider componentProvider;

    @Inject public EditMedicineContract.Presenter presenter;
    @Inject public MedicineEditorViewModel viewModel;
    @Inject public ProductPromptFragment productPromptFragment;
    @Inject public ProductFragment productFragment;

    private long medicineId;

    private EditText titleField;
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

        prepareUi();

        medicineId = getIntent().getLongExtra("MEDICINE_ID", 0);
        if (medicineId > 0)
            presenter.onCurrentDataRequest(medicineId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fab.setOnClickListener(view -> {
            saveMedicine();
            finish();
        });
    }

    private void saveMedicine() {
        presenter.onSaveMedicine(
                medicineId,
                titleField.getText().toString()
        );
    }

    @Override
    public void showTitle(String title) {
        titleField.setText(title);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.editMedicine_action_scan:
                showProductPrompt();
                return true;
        }

        return false;
    }

    private void showProductPrompt() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(productPromptFragment, "ProductPromptFragment")
                .commit();
    }

    private void prepareUi() {
        titleField = findViewById(R.id.editMedicine_title);
        fab = findViewById(R.id.editMedicine_fab);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        bottomAppBar.setOnMenuItemClickListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.editMedicine_productFragmentContainer, productFragment, "ProductFragment")
                .commit();
    }
}
