package com.github.polydome.apteczka.view.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.polydome.apteczka.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton addMedicineButton;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addMedicineButton = findViewById(R.id.mainActivity_fab);
    }

    @Override
    protected void onStart() {
        super.onStart();

        addMedicineButton.setOnClickListener((c) -> showMedicineEditor());
    }

    private void showMedicineEditor() {
        Intent intent = new Intent(this, EditMedicineActivity.class);
        startActivity(intent);
    }
}
