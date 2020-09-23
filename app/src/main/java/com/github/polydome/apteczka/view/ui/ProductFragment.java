package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.polydome.apteczka.databinding.FragmentProductViewBinding;
import com.github.polydome.apteczka.view.ui.medicineeditor.ProductModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class ProductFragment extends Fragment {
    private final ProductModel productModel;

    private FragmentProductViewBinding binding;

    @Inject
    public ProductFragment(@NotNull ProductModel productModel) {
        this.productModel = productModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductViewBinding.inflate(inflater, container, false);
        binding.setModel(productModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}
