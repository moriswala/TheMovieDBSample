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
  private MovieService movieService;

  @Inject
  public MoviesRemoteDataSource(MovieService questionService) {
    this.movieService = questionService;
  }

  @Override
  public Flowable<List<Movie>> loadPopularMovies(boolean forceRemote) {
    return movieService.loadPopularMovies(Config.API_KEY).map(MovieListResponce::getMovies);
  }

  @Override
  public Flowable<List<Movie>> loadTopRatedMovies(boolean forceRemote) {
    return movieService.loadTopRatedMovies(Config.API_KEY).map(MovieListResponce::getMovies);
  }

  @Override
  public Flowable<List<Movie>> searchMovie(boolean forceRemote, String queryText) {
    return movieService.searchMovie(Config.API_KEY, queryText).map(MovieListResponce::getMovies);
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
