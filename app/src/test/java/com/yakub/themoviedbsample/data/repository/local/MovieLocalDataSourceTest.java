package com.yakub.themoviedbsample.data.repository.local;

import com.yakub.themoviedbsample.data.database.MovieDao;
import com.yakub.themoviedbsample.data.model.Movie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class MovieLocalDataSourceTest {

  @Mock
  MovieDao movieDao;

  private MoviesLocalDataSource localDataSource;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);
    localDataSource = new MoviesLocalDataSource(movieDao);
  }

  @Test public void loadQuestions_ShouldReturnFromDatabase() {
    // Given
    List<Movie> movies = Arrays.asList(new Movie(), new Movie());
    TestSubscriber<List<Movie>> subscriber = new TestSubscriber<>();
    given(movieDao.getAllMovies()).willReturn(Flowable.just(movies));

    // When
    localDataSource.loadPopularMovies(false, 1).subscribe(subscriber);

    // Then
    then(movieDao).should().getAllMovies();
  }

  @Test public void addQuestion_ShouldInsertToDatabase() {
    Movie movie = new Movie();
    localDataSource.addMovie(movie);

    then(movieDao).should().insert(movie);
  }

  @Test public void clearData_ShouldDeleteAllDataInDatabase() {
    localDataSource.clearData();

    then(movieDao).should().deleteAll();
  }
}