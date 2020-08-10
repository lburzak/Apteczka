package com.github.polydome.apteczka.view.ui.common;

import androidx.appcompat.app.AppCompatActivity;

import com.github.polydome.apteczka.Apteczka;
import com.github.polydome.apteczka.di.component.ApplicationComponent;
import com.github.polydome.apteczka.di.component.PresentationComponent;

public abstract class BaseActivity extends AppCompatActivity {
    public BaseActivity(int contentLayoutId) {
        super(contentLayoutId);
    }

    private ApplicationComponent getApplicationComponent() {
        return ((Apteczka) getApplication()).getApplicationComponent();
    }

    protected PresentationComponent getPresentationComponent() {
        return getApplicationComponent().createPresentationComponent();
    }
}
