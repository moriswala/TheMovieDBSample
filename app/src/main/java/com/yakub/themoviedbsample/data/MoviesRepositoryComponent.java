package com.yakub.themoviedbsample.data;

import com.yakub.themoviedbsample.AppModule;
import com.yakub.themoviedbsample.data.repository.MoviesRepository;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { MoviesRepositoryModule.class, AppModule.class, ApiServiceModule.class,
    DatabaseModule.class})
public interface MoviesRepositoryComponent {
  MoviesRepository provideMoviesRepository();
}
