package com.yakub.themoviedbsample.data.repository.local;

import com.yakub.themoviedbsample.data.database.MovieDao;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.data.repository.MoviesDataSource;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;

public class MoviesLocalDataSource implements MoviesDataSource {

  private MovieDao movieDao;

  @Inject
  public MoviesLocalDataSource(MovieDao questionDao) {
    this.movieDao = questionDao;
  }

  @Override
  public Flowable<List<Movie>> loadPopularMovies(boolean forceRemote) {
    return movieDao.getAllMovies();
  }

  @Override
  public void addMovie(Movie question) {
    // Insert new one
    movieDao.insert(question);
  }

  @Override
  public void clearData() {
    // Clear old data
    movieDao.deleteAll();
  }
}
