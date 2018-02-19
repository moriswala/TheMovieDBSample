package com.yakub.themoviedbsample.ui.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.GridLayout;
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
  @BindView(R.id.recycler_question) RecyclerView mRecyclerView;
  @BindView(R.id.refresh) SwipeRefreshLayout refreshLayout;
  @BindView(R.id.text_notification) TextView notificationText;

  private MoviesAdapter adapter;
  @Inject
  MoviesPresenter presenter;
  private DrawerLayout mDrawerLayout;
  private int selectedOptionItem;
  private SearchView searchView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initializePresenter();
    setTitle(R.string.title_popular);
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
    setupDrawer();
    adapter = new MoviesAdapter(new ArrayList<>());
//    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//    mRecyclerView.setLayoutManager(layoutManager);

    GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setHasFixedSize(true);
    mRecyclerView.setNestedScrollingEnabled(false);

    mRecyclerView.setAdapter(adapter);
    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    adapter.setOnItemClickListener(
        (view, position) -> presenter.getMovie(adapter.getItem(position).getId()));

    // Refresh layout
    refreshLayout.setOnRefreshListener(() -> {
      switch (selectedOptionItem) {
        case 0:
          presenter.loadPopularMovies(true);
          break;
        case 1:
          presenter.loadTopRatedMovies(true);
          break;
        case 2:
          if(searchView!=null) {
            searchView.setFocusable(true);
            searchView.setIconified(false);
            presenter.searchMovie(true, searchView.getQuery().toString());
          }
          break;
        default:
          break;
      }
    });
    // Set notification text visible first
    notificationText.setVisibility(View.GONE);
  }

  @Override
  public int getSelectedOptionItemIndex(){
    return selectedOptionItem;
  }

  private void setupDrawer() {
    ActionBar ab = getSupportActionBar();
    ab.setHomeAsUpIndicator(R.drawable.ic_menu);
    ab.setDisplayHomeAsUpEnabled(true);

    // Set up the navigation drawer.
    mDrawerLayout = findViewById(R.id.drawer_layout);
    mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
    NavigationView navigationView = findViewById(R.id.nav_view);
    if (navigationView != null) {
      setupDrawerContent(navigationView);
    }
    selectedOptionItem = 0;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        // Open the navigation drawer when the home icon is selected from the toolbar.
        mDrawerLayout.openDrawer(GravityCompat.START);
        if(searchView!=null && !searchView.isIconified()){
            searchView.setIconified(true);
        }
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupDrawerContent(NavigationView navigationView) {

      navigationView.setNavigationItemSelectedListener(
              new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                  selectedOptionItem = menuItem.getItemId();
                  switch (menuItem.getItemId()) {
                    case R.id.popular:
                      // Do nothing, we're already on that screen
                      setTitle(R.string.title_popular);
                      presenter.loadPopularMovies(true);
                      break;
                    case R.id.top:
                      setTitle(R.string.title_top);
                      presenter.loadTopRatedMovies(true);
                      break;
                    case R.id.search:
                      if(searchView!=null) {
                        searchView.setFocusable(true);
                        searchView.setIconified(false);
                      }
                      break;
                    default:
                      break;
                  }
                  // Close the navigation drawer when an item is selected.
                  menuItem.setChecked(true);
                  mDrawerLayout.closeDrawers();
                  return true;
                }
              });

  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.questions, menu);

    // Setup search widget in action bar
    searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
    searchView.setQueryHint(getString(R.string.search_hint));
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
//    searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//      @Override
//      public boolean onClose() {
//        presenter.loadPopularMovies(true);
//        return false;
//      }
//    });
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      public Runnable runnable;
      public String searchText;
      public Handler handler = new Handler();

      @Override public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override public boolean onQueryTextChange(String newText) {

        searchText = newText;
        // Remove all previous callbacks.
        handler.removeCallbacks(runnable);

        runnable = new Runnable() {
          @Override
          public void run() {
            if(searchText!=null && searchText.length()>3)
              presenter.searchMovie(true, searchText);

            if(searchText==null || searchText.length()==0)
              presenter.loadPopularMovies(true);
          }
        };
        handler.postDelayed(runnable, 1000);

        return false;
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
