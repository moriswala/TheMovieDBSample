package com.yakub.themoviedbsample.ui;

import com.yakub.themoviedbsample.data.model.Question;
import com.yakub.themoviedbsample.data.repository.MoviesRepository;
import com.yakub.themoviedbsample.ui.movies.MoviesContract;
import com.yakub.themoviedbsample.ui.movies.MoviesPresenter;

import io.reactivex.Flowable;
import io.reactivex.schedulers.TestScheduler;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

@RunWith(Parameterized.class)
public class QuestionsPresenterTest {
  private static final Question QUESTION1 = new Question();
  private static final Question QUESTION2 = new Question();
  private static final Question QUESTION3 = new Question();
  private static final List<Question> NO_QUESTION = Collections.emptyList();
  private static final List<Question> THREE_QUESTIONS =
      Arrays.asList(QUESTION1, QUESTION2, QUESTION3);

  @Parameters public static Object[] data() {
    return new Object[] { NO_QUESTION, THREE_QUESTIONS };
  }

  @Parameter public List<Question> questions;

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
    given(repository.loadPopularMovies(true)).willReturn(Flowable.just(questions));

    // When
    presenter.loadPopularMovies(false, true);
    testScheduler.triggerActions();

    // Then
    then(view).should(atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void loadQuestions_ShouldShowQuestionOnView_WithDataReturned() {
    // Given
    given(repository.loadPopularMovies(true)).willReturn(Flowable.just(THREE_QUESTIONS));

    // When
    presenter.loadPopularMovies(false, true);
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should().showMovies(THREE_QUESTIONS);
    then(view).should(atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void loadQuestions_ShouldShowMessage_WhenNoDataReturned() {
    // Given
    given(repository.loadPopularMovies(true)).willReturn(Flowable.just(NO_QUESTION));

    // When
    presenter.loadPopularMovies(false, true);
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should(never()).showMovies(any());
    then(view).should().showNoDataMessage();
    then(view).should(atLeastOnce()).stopLoadingIndicator();
  }

  @Test public void getQuestion_ShouldShowDetailOnView() {
    // Given
    given(repository.getMovie(1)).willReturn(Flowable.just(QUESTION1));

    // When
    presenter.getMovie(1);
    testScheduler.triggerActions();

    // Then
    then(view).should().showMovieDetail(QUESTION1);
  }

  @Test public void search_ResultShouldBeShownOnView_WhenFilteredDataIsNotEmpty() {
    // Given
    QUESTION1.setTitle("activity onCreate");
    QUESTION2.setTitle("activity onDestroy");
    QUESTION3.setTitle("fragment");
    given(repository.loadPopularMovies(false)).willReturn(Flowable.just(THREE_QUESTIONS));

    // When
    presenter.search("activity");
    testScheduler.triggerActions();

    // Then
    // Return a list of questions which should contains only question 1.
    then(view).should().showMovies(Arrays.asList(QUESTION1, QUESTION2));
    then(view).shouldHaveNoMoreInteractions();
  }

  @Test public void search_EmptyMessageShouldBeShownOnView_WhenDataIsEmpty() {
    // Given
    given(repository.loadPopularMovies(false)).willReturn(Flowable.just(NO_QUESTION));

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
    QUESTION1.setTitle("activity onCreate");
    QUESTION2.setTitle("activity onDestroy");
    QUESTION3.setTitle("fragment");
    given(repository.loadPopularMovies(false)).willReturn(Flowable.just(NO_QUESTION));

    // When
    presenter.search("invalid question");
    testScheduler.triggerActions();

    // Then
    then(view).should().clearMovies();
    then(view).should().showEmptySearchResult();
    then(view).shouldHaveNoMoreInteractions();
  }
}
