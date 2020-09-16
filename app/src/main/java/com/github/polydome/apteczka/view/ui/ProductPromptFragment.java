package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.polydome.apteczka.databinding.FragmentProductPromptBinding;
import com.github.polydome.apteczka.view.ui.medicineeditor.EanInputListener;

public class ProductPromptFragment extends Fragment {
    private final EanInputListener inputListener;

    public ProductPromptFragment(EanInputListener inputListener) {
        this.inputListener = inputListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentProductPromptBinding binding = FragmentProductPromptBinding.inflate(inflater, container, false);
        binding.setInputListener(inputListener);
        return binding.getRoot();
    }
}
