package com.yakub.themoviedbsample.ui.splash;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2/20/2018.
 */
@Module
public class SplashPresenterModule {
    private SplashContract.View view;

    public SplashPresenterModule(SplashContract.View view) {
        this.view = view;
    }

    @Provides
    public SplashContract.View provideView() {
        return view;
    }
}
