package com.yakub.themoviedbsample.data.repository.remote;

import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.api.MovieListResponce;
import com.yakub.themoviedbsample.data.api.MovieService;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.data.repository.MoviesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class MoviesRemoteDataSource implements MoviesDataSource {
  private MovieService questionService;

  @Inject
  public MoviesRemoteDataSource(MovieService questionService) {
    this.questionService = questionService;
  }

  @Override
  public Flowable<List<Movie>> loadPopularMovies(boolean forceRemote) {
    return questionService.loadPopularMovies(Config.API_KEY).map(MovieListResponce::getMovies);
  }

  @Override
  public void addMovie(Movie question) {
    //Currently, we do not need this for remote source.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override
  public void clearData() {
    //Currently, we do not need this for remote source.
    throw new UnsupportedOperationException("Unsupported operation");
  }
}
