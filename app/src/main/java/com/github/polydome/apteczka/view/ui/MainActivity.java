package com.github.polydome.apteczka.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
        Context context = this;

        addMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMedicineActivity.class);
                context.startActivity(intent);
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
