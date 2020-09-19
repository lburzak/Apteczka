package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.polydome.apteczka.databinding.FragmentProductPromptBinding;
import com.github.polydome.apteczka.view.ui.medicineeditor.EanInputListener;
import com.github.polydome.apteczka.view.ui.medicineeditor.ProductStatus;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

public class ProductPromptFragment extends BottomSheetDialogFragment {
    private final EanInputListener inputListener;
    private final ProductStatus.Owner productStatusOwner;

    @Inject
    public ProductPromptFragment(EanInputListener inputListener, ProductStatus.Owner productStatusOwner) {
        this.inputListener = inputListener;
        this.productStatusOwner = productStatusOwner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentProductPromptBinding binding = FragmentProductPromptBinding.inflate(inflater, container, false);
        binding.setInputListener(inputListener);
        binding.setProductStatus(productStatusOwner.getProductStatus());
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }
}
