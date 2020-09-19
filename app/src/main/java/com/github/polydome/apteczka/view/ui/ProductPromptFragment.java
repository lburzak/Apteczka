package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.polydome.apteczka.R;
import com.github.polydome.apteczka.databinding.FragmentProductPromptBinding;
import com.github.polydome.apteczka.view.ui.medicineeditor.EanInputListener;
import com.github.polydome.apteczka.view.ui.medicineeditor.ProductStatus;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

public class ProductPromptFragment extends BottomSheetDialogFragment {
    private final EanInputListener inputListener;
    private final ProductStatus.Owner productStatusOwner;

    private FragmentProductPromptBinding binding;

    @Inject
    public ProductPromptFragment(EanInputListener inputListener, ProductStatus.Owner productStatusOwner) {
        this.inputListener = inputListener;
        this.productStatusOwner = productStatusOwner;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductPromptBinding.inflate(inflater, container, false);
        binding.setInputListener(inputListener);
        binding.setProductStatus(productStatusOwner.getProductStatus());

        // FIXME: 9/19/20 Should be fragment instead
        binding.setLifecycleOwner(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        productStatusOwner.getProductStatus().observe(this, status -> {
            switch (status) {
                case LINKED:
                    binding.productPromptStatus.setText(R.string.productPrompt_status_alreadyLinked);
                    break;
                case UNRECOGNIZED:
                    binding.productPromptStatus.setText(R.string.productPrompt_status_notAvailable);
                    break;
                case EMPTY:
                    binding.productPromptStatus.setText("");
                    break;
            }
        });
    }
}
