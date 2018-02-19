package com.yakub.themoviedbsample.data.repository.local;

import com.yakub.themoviedbsample.data.database.MovieDao;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.data.repository.MoviesDataSource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class MoviesLocalDataSource implements MoviesDataSource {

  private MovieDao movieDao;

  @Inject
  public MoviesLocalDataSource(MovieDao questionDao) {
    this.movieDao = questionDao;
  }

  @Override
  public Flowable<List<Movie>> loadPopularMovies(boolean forceRemote) {
    return movieDao.getAllPopularMovies();
  }

  @Override
  public Flowable<List<Movie>> loadTopRatedMovies(boolean forceRemote) {
    return movieDao.getAllTopRatedMovies();
  }

  @Override
  public Flowable<List<Movie>> searchMovie(boolean forceRemote, String queryText) {
    return movieDao.searchMovie("%"+queryText+"%");
  }

  @Override
  public void addMovie(Movie movie) {
    // Insert new one
    movieDao.insert(movie);
  }

  @Override
  public void clearData() {
    // Clear old data
    movieDao.deleteAll();
  }
}
