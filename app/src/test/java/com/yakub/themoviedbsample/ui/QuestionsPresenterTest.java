package com.yakub.themoviedbsample.ui;

import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.data.repository.MoviesRepository;
import com.yakub.themoviedbsample.ui.movies.MoviesContract;
import com.yakub.themoviedbsample.ui.movies.MoviesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

@RunWith(Parameterized.class)
public class QuestionsPresenterTest {
  private static final Movie MOVIE1 = new Movie();
  private static final Movie MOVIE2 = new Movie();
  private static final Movie MOVIE3 = new Movie();
  private static final List<Movie> NO_MOVIE = Collections.emptyList();
  private static final List<Movie> THREE_MOVIE =
      Arrays.asList(MOVIE1, MOVIE2, MOVIE3);

  @Parameters public static Object[] data() {
    return new Object[] {NO_MOVIE, THREE_MOVIE};
  }

  @Parameter public List<Movie> movies;

  @Mock private MoviesRepository repository;

  @Mock private MoviesContract.View view;

  private TestScheduler testScheduler;

  private MoviesPresenter presenter;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    testScheduler = new TestScheduler();
    presenter = new MoviesPresenter(repository, view, testScheduler, testScheduler);
  }

  @Test public void loadQuestions_ShouldAlwaysStopLoadingIndicatorOnView_WhenComplete() {
    // Given
    given(repository.loadPopularMovies(true,1)).willReturn(Flowable.just(movies));

    // When
    presenter.loadPopularMovies(false, 1);
    testScheduler.triggerActions();

    // Then
    then(view).should(atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void loadQuestions_ShouldShowQuestionOnView_WithDataReturned() {
    // Given
    given(repository.loadPopularMovies(true,1)).willReturn(Flowable.just(THREE_MOVIE));

    // When
    presenter.loadPopularMovies(false, 1);
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should().showMovies(THREE_MOVIE);
    then(view).should(atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void loadQuestions_ShouldShowMessage_WhenNoDataReturned() {
    // Given
    given(repository.loadPopularMovies(true,1)).willReturn(Flowable.just(NO_MOVIE));

    // When
    presenter.loadPopularMovies(false, 1);
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should(never()).showMovies(any());
    then(view).should().showNoDataMessage();
    then(view).should(atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void getQuestion_ShouldShowDetailOnView() {
    // Given
    given(repository.getMovie(true, 1)).willReturn(Flowable.just(MOVIE1));

    // When
    presenter.getMovie(false,1);
    testScheduler.triggerActions();

    // Then
    then(view).should().showMovieDetail(MOVIE1);
  }

  @Test public void search_ResultShouldBeShownOnView_WhenFilteredDataIsNotEmpty() {
    // Given
    MOVIE1.setTitle("activity onCreate");
    MOVIE2.setTitle("activity onDestroy");
    MOVIE3.setTitle("fragment");
    given(repository.loadPopularMovies(false,1)).willReturn(Flowable.just(THREE_MOVIE));

    // When
    presenter.search("activity");
    testScheduler.triggerActions();

    // Then
    // Return a list of movies which should contains only question 1.
    then(view).should().showMovies(Arrays.asList(MOVIE1, MOVIE2));
    then(view).shouldHaveNoMoreInteractions();
  }

  @Test public void search_EmptyMessageShouldBeShownOnView_WhenDataIsEmpty() {
    // Given
    given(repository.loadPopularMovies(true, 1)).willReturn(Flowable.just(NO_MOVIE));

    // When
    presenter.search(any());
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should().showEmptySearchResult();
    then(view).shouldHaveNoMoreInteractions();
  }

  @Test public void search_EmptyMessageShouldBeShownOnView_WhenNoDataMatchesQuery() {
    // Given
    MOVIE1.setTitle("activity onCreate");
    MOVIE2.setTitle("activity onDestroy");
    MOVIE3.setTitle("fragment");
    given(repository.loadPopularMovies(false,1)).willReturn(Flowable.just(NO_MOVIE));

    // When
    presenter.search("invalid question");
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should().showEmptySearchResult();
    then(view).shouldHaveNoMoreInteractions();
  }
}
