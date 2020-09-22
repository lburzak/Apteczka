package com.github.polydome.apteczka.view.test.util;

import java.util.Objects;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposables;

public class FakeMaybe<T> extends Maybe<T> {
    private MaybeObserver<? super T> observer;

    @Override
    protected void subscribeActual(MaybeObserver<? super T> observer) {
        if (this.observer == null) {
            this.observer = observer;
            observer.onSubscribe(Disposables.empty());
        } else {
            throw new UnsupportedOperationException("Multiple observers not supported");
        }
    }

    public void pushComplete() {
        Objects.requireNonNull(observer);
        observer.onComplete();
    }

    public void pushSuccess(T value) {
        Objects.requireNonNull(observer);
        observer.onSuccess(value);
    }

    public void pushError(Throwable t) {
        Objects.requireNonNull(observer);
        observer.onError(t);
    }
}
