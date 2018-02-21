package com.yakub.themoviedbsample.ui.movies;

import com.yakub.themoviedbsample.data.MoviesRepositoryComponent;
import com.yakub.themoviedbsample.ui.base.ActivityScope;
import com.yakub.themoviedbsample.util.schedulers.SchedulerModule;
import dagger.Component;

@ActivityScope
@Component(modules = {MoviesPresenterModule.class, SchedulerModule.class}, dependencies = {
    MoviesRepositoryComponent.class
})
public interface MoviesComponent {
  void inject(MoviesActivity questionsActivity);
}
