package com.github.polydome.apteczka.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.polydome.apteczka.databinding.FragmentProductViewBinding;
import com.github.polydome.apteczka.view.ui.common.PresentationComponentProvider;
import com.github.polydome.apteczka.view.viewmodel.ProductViewModel;

import javax.inject.Inject;

public class ProductFragment extends Fragment {
    public static final String BUNDLE_KEY_MEDICINE_ID = "MEDICINE_ID";

    @Inject public ViewModelProvider.Factory viewModelFactory;

    private ProductViewModel viewModel;
    private FragmentProductViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new PresentationComponentProvider(this)
                .getPresentationComponent()
                .inject(this);

        viewModel =
                new ViewModelProvider(this, viewModelFactory).get(ProductViewModel.class);

        long medicineId = retrieveMedicineId();
        viewModel.changeMedicineId(medicineId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductViewBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    private long retrieveMedicineId() {
        if (getArguments() != null) {
            return getArguments().getLong(BUNDLE_KEY_MEDICINE_ID);
        } else
            throw new IllegalArgumentException("Medicine ID not specified");
    }
}
