package com.yakub.themoviedbsample.data.repository;

import android.support.annotation.VisibleForTesting;

import com.yakub.themoviedbsample.data.model.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class MoviesRepository implements MoviesDataSource {

  private MoviesDataSource remoteDataSource;
  private MoviesDataSource localDataSource;

  @VisibleForTesting List<Movie> caches;

  @Inject public MoviesRepository(@Local MoviesDataSource localDataSource,
                                  @Remote MoviesDataSource remoteDataSource) {
    this.localDataSource = localDataSource;
    this.remoteDataSource = remoteDataSource;

    caches = new ArrayList<>();
  }

  @Override public Flowable<List<Movie>> loadPopularMovies(boolean forceRemote) {
    if (forceRemote) {
      return refreshData();
    } else {
      if (caches.size() > 0) {
        // if cache is available, return it immediately
        return Flowable.just(caches);
      } else {
        // else return data from local storage
        return localDataSource.loadPopularMovies(false)
            .take(1)
            .flatMap(Flowable::fromIterable)
            .doOnNext(question -> caches.add(question))
            .toList()
            .toFlowable()
            .filter(list -> !list.isEmpty())
            .switchIfEmpty(
                refreshData()); // If local data is empty, fetch from remote source instead.
      }
    }
  }

  /**
   * Fetches data from remote source.
   * Save it into both local database and cache.
   *
   * @return the Flowable of newly fetched data.
   */
  Flowable<List<Movie>> refreshData() {
    return remoteDataSource.loadPopularMovies(true).doOnNext(list -> {
      // Clear cache
      caches.clear();
      // Clear data in local storage
      localDataSource.clearData();
    }).flatMap(Flowable::fromIterable).doOnNext(question -> {
      caches.add(question);
      localDataSource.addMovie(question);
    }).toList().toFlowable();
  }

  /**
   * Loads a question by its question id.
   *
   * @param movieId question's id.
   * @return a corresponding question from cache.
   */
  public Flowable<Movie> getMovie(long movieId) {
    return Flowable.fromIterable(caches).filter(movie -> movie.getId() == movieId);
  }

  @Override public void addMovie(Movie question) {
    //Currently, we do not need this.
    throw new UnsupportedOperationException("Unsupported operation");
  }

  @Override public void clearData() {
    caches.clear();
    localDataSource.clearData();
  }
}
