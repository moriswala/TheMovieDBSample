package com.yakub.themoviedbsample.data.repository;

import com.yakub.themoviedbsample.data.model.Movie;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class MovieRepositoryTest {
  private static final Movie question1 = new Movie();
  private static final Movie question2 = new Movie();
  private static final Movie question3 = new Movie();
  private static final List<Movie> questions = Arrays.asList(question1, question2, question3);

  @Mock @Local private MoviesDataSource localDataSource;

  @Mock @Remote private MoviesDataSource remoteDataSource;

  private MoviesRepository repository;

  private TestSubscriber<List<Movie>> questionsTestSubscriber;

  @Before public void setup() {
    MockitoAnnotations.initMocks(this);

    repository = new MoviesRepository(localDataSource, remoteDataSource);

    questionsTestSubscriber = new TestSubscriber<>();
  }

  @Test public void loadQuestions_ShouldReturnCache_IfItIsAvailable() {
    // Given
    repository.caches.addAll(questions);

    // When
    repository.loadPopularMovies(false).subscribe(questionsTestSubscriber);

    // Then
    // No interaction with local storage or remote source
    verifyZeroInteractions(localDataSource);
    verifyZeroInteractions(remoteDataSource);

    questionsTestSubscriber.assertValue(questions);
  }

  @Test public void loadQuestions_ShouldReturnFromLocal_IfCacheIsNotAvailable() {
    // Given
    // No cache
    doReturn(Flowable.just(questions)).when(localDataSource).loadPopularMovies(false);
    doReturn(Flowable.just(questions)).when(remoteDataSource).loadPopularMovies(true);

    // When
    repository.loadPopularMovies(false).subscribe(questionsTestSubscriber);

    // Then
    // Loads from local storage
    verify(localDataSource).loadPopularMovies(false);
    // Will load from remote source if there is no local data available
    verify(remoteDataSource).loadPopularMovies(true);

    questionsTestSubscriber.assertValue(questions);
  }

  @Test public void loadQuestions_ShouldReturnFromRemote_WhenItIsRequired() {
    // Given
    doReturn(Flowable.just(questions)).when(remoteDataSource).loadPopularMovies(true);

    // When
    repository.loadPopularMovies(true).subscribe(questionsTestSubscriber);

    // Then
    // Load from remote not from local storage
    verify(remoteDataSource).loadPopularMovies(true);
    verify(localDataSource, never()).loadPopularMovies(true);
    // Cache and local storage data are clear and are filled with new data
    verify(localDataSource).clearData();
    assertEquals(repository.caches, questions);
    verify(localDataSource).addMovie(question1);
    verify(localDataSource).addMovie(question2);
    verify(localDataSource).addMovie(question3);

    questionsTestSubscriber.assertValue(questions);
  }

  @Test public void getQuestion_ShouldReturnFromCache() {
    // Given
    question1.setId(1);
    question2.setId(2);
    question3.setId(3);
    repository.caches.addAll(questions);
    TestSubscriber<Movie> subscriber = new TestSubscriber<>();

    // When
    repository.getMovie(1).subscribe(subscriber);

    // Then
    // No interaction with local storage or remote source
    then(localDataSource).shouldHaveZeroInteractions();
    then(remoteDataSource).shouldHaveZeroInteractions();
    // Should return correct question
    subscriber.assertValue(question1);
  }

  @Test public void refreshData_ShouldClearOldDataFromLocal() {
    // Given
    given(remoteDataSource.loadPopularMovies(true)).willReturn(Flowable.just(questions));

    // When
    repository.refreshPopularMoviesData().subscribe(questionsTestSubscriber);

    // Then
    then(localDataSource).should().clearData();
  }

  @Test public void refreshData_ShouldAddNewDataToCache() {
    // Given
    given(remoteDataSource.loadPopularMovies(true)).willReturn(Flowable.just(questions));

    // When
    repository.refreshPopularMoviesData().subscribe(questionsTestSubscriber);

    // Then
    assertThat(repository.caches, equalTo(questions));
  }

  @Test public void refreshData_ShouldAddNewDataToLocal() {
    // Given
    given(remoteDataSource.loadPopularMovies(true)).willReturn(Flowable.just(questions));

    // When
    repository.refreshPopularMoviesData().subscribe(questionsTestSubscriber);

    // Then
    then(localDataSource).should().addMovie(question1);
    then(localDataSource).should().addMovie(question2);
    then(localDataSource).should().addMovie(question3);
  }

  @Test public void clearData_ShouldClearCachesAndLocalData() {
    // Given
    repository.caches.addAll(questions);

    // When
    repository.clearData();

    // Then
    assertThat(repository.caches, empty());
    then(localDataSource).should().clearData();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void addQuestion_ShouldThrowException() {
    repository.addMovie(question1);
  }
}
