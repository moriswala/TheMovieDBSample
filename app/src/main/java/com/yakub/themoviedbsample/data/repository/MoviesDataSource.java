package com.yakub.themoviedbsample.data.repository;

import com.yakub.themoviedbsample.data.model.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface MoviesDataSource {
  Flowable<List<Movie>> loadPopularMovies(boolean forceRemote);

  void addMovie(Movie question);

  void clearData();
}
