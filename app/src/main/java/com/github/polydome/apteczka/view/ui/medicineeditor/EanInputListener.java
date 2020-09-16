package com.github.polydome.apteczka.view.ui.medicineeditor;

import androidx.lifecycle.MutableLiveData;

public interface EanInputListener {
    MutableLiveData<String> getEan();
    void onEanInput();
    void onEanCleared();
}
