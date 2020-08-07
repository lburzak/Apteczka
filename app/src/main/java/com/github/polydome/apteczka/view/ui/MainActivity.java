package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.github.polydome.apteczka.R;

public class MainActivity extends AppCompatActivity {
    private Button addMedicineButton;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addMedicineButton = findViewById(R.id.add_medicine);
    }

    @Override
    protected void onStart() {
        super.onStart();

        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEanInputDialog();
            }
        });
    }

    private void showEanInputDialog() {
        DialogFragment dialogFragment = new EanInputDialog();
        getSupportFragmentManager()
                .beginTransaction()
                .add(dialogFragment, "EanInputDialog")
                .commit();
    }
}
