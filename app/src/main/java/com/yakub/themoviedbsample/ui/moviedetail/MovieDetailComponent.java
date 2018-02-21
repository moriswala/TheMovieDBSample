package com.yakub.themoviedbsample.ui.moviedetail;

import com.yakub.themoviedbsample.data.MoviesRepositoryComponent;
import com.yakub.themoviedbsample.ui.base.ActivityScope;
import com.yakub.themoviedbsample.util.schedulers.SchedulerModule;

import dagger.Component;

@ActivityScope
@Component(modules = {MovieDetailPresenterModule.class, SchedulerModule.class}, dependencies = {
        MoviesRepositoryComponent.class
})
public interface MovieDetailComponent {
  void inject(MovieDetailActivity movieDetailActivity);
}
