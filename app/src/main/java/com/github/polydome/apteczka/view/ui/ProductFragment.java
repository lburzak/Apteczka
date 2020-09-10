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
import com.github.polydome.apteczka.view.viewmodel.PersistedProductViewModel;
import com.github.polydome.apteczka.view.viewmodel.PreviewProductViewModel;
import com.github.polydome.apteczka.view.viewmodel.ProductViewModel;

import javax.inject.Inject;

public class ProductFragment extends Fragment {
    public static final String BUNDLE_KEY_MEDICINE_ID = "com.github.polydome.apteczka.KEY_MEDICINE_ID";
    public static final String BUNDLE_KEY_EAN = "com.github.polydome.apteczka.KEY_EAN";

    @Inject public ViewModelProvider.Factory viewModelFactory;

    private ProductViewModel viewModel;
    private FragmentProductViewBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new PresentationComponentProvider(this)
                .getPresentationComponent()
                .inject(this);

        long medicineId = retrieveMedicineId();

        if (medicineId > 0) {
            viewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(PersistedProductViewModel.class);
            ((PersistedProductViewModel) viewModel).setMedicineId(medicineId);
        } else {
            viewModel = new ViewModelProvider(this, viewModelFactory)
                    .get(PreviewProductViewModel.class);
            ((PreviewProductViewModel) viewModel).setEan(retrieveEan());
        }
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

    private String retrieveEan() {
        if (getArguments() != null) {
            return getArguments().getString(BUNDLE_KEY_EAN);
        } else
            throw new IllegalArgumentException("Medicine ID not specified");
    }
}
