package com.github.polydome.apteczka.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

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

    @Override
    public void onStart() {
        super.onStart();
        eanInputEditText = requireDialog().findViewById(R.id.ean_input);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detach();
    }

    @Override
    @UiThread
    public void showMedicineEditor(long medicineId) {
        Intent intent = new Intent(requireActivity(), EditMedicineActivity.class);
        intent.putExtra("MEDICINE_ID", medicineId);
        requireActivity().startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_ean_input, container, true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.ean_input_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateMedicine(getEanInput());
            }
        });

        eanInputEditText = view.findViewById(R.id.ean_input);
    }

    private String getEanInput() {
        return eanInputEditText.getText().toString();
    }
}
