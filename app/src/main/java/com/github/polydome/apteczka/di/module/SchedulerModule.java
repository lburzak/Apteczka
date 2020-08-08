package com.github.polydome.apteczka.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulerModule {
    @Provides
    @Named("ioScheduler")
    public Scheduler ioScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Named("uiScheduler")
    public Scheduler uiScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
