package com.yakub.themoviedbsample.data;

import com.yakub.themoviedbsample.data.repository.Local;
import com.yakub.themoviedbsample.data.repository.MoviesDataSource;
import com.yakub.themoviedbsample.data.repository.Remote;
import com.yakub.themoviedbsample.data.repository.local.MoviesLocalDataSource;
import com.yakub.themoviedbsample.data.repository.remote.MoviesRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MoviesRepositoryModule {

  @Provides
  @Local
  @Singleton
  public MoviesDataSource provideLocalDataSource(MoviesLocalDataSource moviesLocalDataSource) {
    return moviesLocalDataSource;
  }

  @Provides
  @Remote
  @Singleton
  public MoviesDataSource provideRemoteDataSource(MoviesRemoteDataSource moviesRemoteDataSource) {
    return moviesRemoteDataSource;
  }

}
