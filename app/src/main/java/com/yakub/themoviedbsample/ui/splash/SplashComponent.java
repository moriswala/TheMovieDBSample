package com.yakub.themoviedbsample.ui.splash;

import com.yakub.themoviedbsample.data.MoviesRepositoryComponent;
import com.yakub.themoviedbsample.ui.base.ActivityScope;
import com.yakub.themoviedbsample.util.schedulers.SchedulerModule;

import dagger.Component;

@ActivityScope
@Component(modules = {SplashPresenterModule.class, SchedulerModule.class}, dependencies = {
        MoviesRepositoryComponent.class
})
public interface SplashComponent {
  void inject(SplashActivity splashActivity);
}
