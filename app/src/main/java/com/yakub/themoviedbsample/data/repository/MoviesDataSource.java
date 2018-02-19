package com.yakub.themoviedbsample.data.repository;

import com.yakub.themoviedbsample.data.model.Movie;

import java.util.List;

import io.reactivex.Flowable;

public interface MoviesDataSource {
  Flowable<List<Movie>> loadPopularMovies(boolean forceRemote);

    Flowable<List<Movie>> loadTopRatedMovies(boolean forceRemote);

    Flowable<List<Movie>> searchMovie(boolean forceRemote, String queryText);

    void addMovie(Movie question);

  void clearData();
}
