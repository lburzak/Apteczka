package com.github.polydome.apteczka.view.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.view.contract.AddMedicineContract;
import com.github.polydome.apteczka.view.ui.common.BaseDialogFragment;

import javax.inject.Inject;

public class EanInputDialog extends BaseDialogFragment implements AddMedicineContract.View {
    @Inject
    public AddMedicineContract.Presenter presenter;
    private EditText eanInputEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresentationComponent().inject(this);
        presenter.attach(this);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return buildSelf();
    }

    @Override
    public void onStart() {
        super.onStart();
        eanInputEditText = requireDialog().findViewById(R.id.ean_input);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showMedicineEditor(long medicineId) {
        Intent intent = new Intent(requireActivity(), EditMedicineActivity.class);
        requireActivity().startActivity(intent);
    }

    private Dialog buildSelf() {
        return new AlertDialog.Builder(requireActivity())
                .setTitle("Add medicine")
                .setView(R.layout.dialog_ean_input)
                .setPositiveButton("Add", new AddButtonListener())
                .create();
    }

    private String getEanInput() {
        return eanInputEditText.getText().toString();
    }

    private class AddButtonListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            presenter.onCreateMedicine(getEanInput());
        }
    }
}
