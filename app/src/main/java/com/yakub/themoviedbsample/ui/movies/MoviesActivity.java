package com.yakub.themoviedbsample.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.yakub.themoviedbsample.R;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesActivity extends BaseActivity implements MoviesContract.View {
  @BindView(R.id.recycler_question) RecyclerView questionRecyclerView;
  @BindView(R.id.refresh) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.text_notification) TextView notificationText;

  private MoviesAdapter adapter;
  @Inject
  MoviesPresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initializePresenter();
    setTitle(getString(R.string.app_name));
    setupWidgets();
  }

  private void initializePresenter() {
    DaggerMoviesComponent.builder()
        .moviesPresenterModule(new MoviesPresenterModule(this))
        .moviesRepositoryComponent(getQuestionRepositoryComponent())
        .build()
        .inject(this);
  }

  private void setupWidgets() {
    // Setup recycler view
    adapter = new MoviesAdapter(new ArrayList<>());
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    questionRecyclerView.setLayoutManager(layoutManager);
    questionRecyclerView.setAdapter(adapter);
    questionRecyclerView.setItemAnimator(new DefaultItemAnimator());
    adapter.setOnItemClickListener(
        (view, position) -> presenter.getMovie(adapter.getItem(position).getId()));

    // Refresh layout
    refreshLayout.setOnRefreshListener(() -> presenter.loadPopularMovies(true));
    // Set notification text visible first
    notificationText.setVisibility(View.GONE);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.questions, menu);

    // Setup search widget in action bar
    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    searchView.setQueryHint(getString(R.string.search_hint));
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {
        presenter.search(newText);
        return true;
      }
    });

    return true;
  }

  @Override public void showMovies(List<Movie> movies) {
    notificationText.setVisibility(View.GONE);
    adapter.replaceData(movies);
  }

  @Override public void showNoDataMessage() {
    showNotification(getString(R.string.msg_no_data));
  }

  @Override public void showErrorMessage(String error) {
    showNotification(error);
  }

  @Override public void clearMovies() {
    adapter.clearData();
  }

  @Override public void stopLoadingIndicator() {
    if (refreshLayout.isRefreshing()) {
      refreshLayout.setRefreshing(false);
    }
  }

  @Override public void showMovieDetail(Movie movie) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
//    intent.setData(Uri.parse(movie.get));
    startActivity(intent);
  }

  @Override public void showEmptySearchResult() {
    showNotification(getString(R.string.msg_empty_search_result));
  }

  private void showNotification(String message) {
    notificationText.setVisibility(View.VISIBLE);
    notificationText.setText(message);
  }
}
