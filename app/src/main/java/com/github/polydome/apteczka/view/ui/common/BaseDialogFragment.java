package com.github.polydome.apteczka.view.ui.common;

import androidx.fragment.app.DialogFragment;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.di.component.ApplicationComponent;
import com.github.polydome.apteczka.di.component.PresentationComponent;

public abstract class BaseDialogFragment extends DialogFragment {
    private ApplicationComponent getApplicationComponent() {
        return ((Apteczka) requireActivity().getApplication()).getApplicationComponent();
    }

    protected PresentationComponent getPresentationComponent() {
        return getApplicationComponent().createPresentationComponent();
    }
}
