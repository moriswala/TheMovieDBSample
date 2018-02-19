package com.yakub.themoviedbsample.data.repository.remote;

import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.api.MovieListResponce;
import com.yakub.themoviedbsample.data.api.MovieService;
import com.yakub.themoviedbsample.data.model.Movie;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class MovieRemoteDataSourceTest {
  @Mock
  MovieService movieService;

  private MoviesRemoteDataSource remoteDataSource;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    remoteDataSource = new MoviesRemoteDataSource(movieService);
  }

  @Test
  public void loadQuestions_ShouldReturnFromRemoteService() {
    MovieListResponce movieListResponce = new MovieListResponce();
    TestSubscriber<List<Movie>> subscriber = new TestSubscriber<>();
    given(movieService.loadPopularMovies(Config.API_KEY)).willReturn(Flowable.just(movieListResponce));

    remoteDataSource.loadPopularMovies(anyBoolean()).subscribe(subscriber);

    then(movieService).should().loadPopularMovies(Config.API_KEY);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void addQuestion_NoThingToDoWithRemoteService() {
    Movie movie = mock(Movie.class);
    remoteDataSource.addMovie(movie);

    then(movieService).shouldHaveZeroInteractions();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void clearData_NoThingToDoWithRemoteService() {
    remoteDataSource.clearData();

    then(movieService).shouldHaveZeroInteractions();
  }
}