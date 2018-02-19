package com.yakub.themoviedbsample.ui.movies;

import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.ui.base.BasePresenter;
import java.util.List;

public interface MoviesContract {
  interface View {
    int getSelectedOptionItemIndex();

    void showMovies(List<Movie> questions);

    void clearMovies();

    void showNoDataMessage();

    void showErrorMessage(String error);

    void showMovieDetail(Movie question);

    void stopLoadingIndicator();

    void showEmptySearchResult();
  }

  interface Presenter extends BasePresenter<MoviesContract.View> {
    void loadPopularMovies(boolean onlineRequired);

    void loadTopRatedMovies(boolean onlineRequired);

    void searchMovie(boolean onlineRequired, String queryText);

    void getMovie(long questionId);

    void search(String questionTitle);
  }
}
