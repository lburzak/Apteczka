package com.github.polydome.apteczka.view.presenter.common;

import com.github.polydome.apteczka.view.contract.common.Contract;

public abstract class Presenter <V extends Contract.View> implements Contract.Presenter<V> {
    private V view;

    protected V requireView() {
        if (this.view == null)
            throw new IllegalStateException("No view attached");
        else
            return view;
    }

    @Override
    public void attach(V view) {
        this.view = view;
    }

    @Override
    public void detach() {

    }
}
