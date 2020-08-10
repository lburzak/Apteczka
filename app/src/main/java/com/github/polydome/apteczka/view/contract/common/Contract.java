package com.github.polydome.apteczka.view.contract.common;

public interface Contract {
    interface View {}

    interface Presenter <V extends View> {
        void attach(V view);
        void detach();
    }
}
