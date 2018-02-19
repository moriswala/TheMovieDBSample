package com.yakub.themoviedbsample.ui.movies;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.data.repository.MoviesRepository;
import com.yakub.themoviedbsample.util.schedulers.RunOn;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.yakub.themoviedbsample.util.schedulers.SchedulerType.IO;
import static com.yakub.themoviedbsample.util.schedulers.SchedulerType.UI;

/**
 * A presenter with life-cycle aware.
 */
public class MoviesPresenter implements MoviesContract.Presenter, LifecycleObserver {

  private MoviesRepository repository;

  private MoviesContract.View view;

  private Scheduler ioScheduler;
  private Scheduler uiScheduler;

  private CompositeDisposable disposeBag;

  @Inject public MoviesPresenter(MoviesRepository repository, MoviesContract.View view,
                                 @RunOn(IO) Scheduler ioScheduler, @RunOn(UI) Scheduler uiScheduler) {
    this.repository = repository;
    this.view = view;
    this.ioScheduler = ioScheduler;
    this.uiScheduler = uiScheduler;

    // Initialize this presenter as a lifecycle-aware when a view is a lifecycle owner.
    if (view instanceof LifecycleOwner) {
      ((LifecycleOwner) view).getLifecycle().addObserver(this);
    }

    disposeBag = new CompositeDisposable();
  }

  @Override @OnLifecycleEvent(Lifecycle.Event.ON_RESUME) public void onAttach() {
    loadPopularMovies( false);
  }

  @Override @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) public void onDetach() {
    // Clean up any no-longer-use resources here
    disposeBag.clear();
  }


  @Override public void loadPopularMovies(boolean onlineRequired) {
    // Clear old data on view
    view.clearMovies();

    // Load new one and populate it into view
    Disposable disposable = repository.loadPopularMovies(onlineRequired)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
    disposeBag.add(disposable);
  }

  @Override public void loadTopRatedMovies(boolean onlineRequired) {
    // Clear old data on view
    view.clearMovies();

    // Load new one and populate it into view
    Disposable disposable = repository.loadTopRatedMovies(onlineRequired)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
    disposeBag.add(disposable);
  }

  @Override public void searchMovie(boolean onlineRequired, String queryText) {
    // Clear old data on view
    view.clearMovies();

    // Load new one and populate it into view
    Disposable disposable = repository.searchMovie(onlineRequired, queryText)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribe(this::handleReturnedData, this::handleError, () -> view.stopLoadingIndicator());
    disposeBag.add(disposable);
  }

  @Override public void getMovie(long movieId) {
    Disposable disposable = repository.getMovie(movieId)
        .filter(movie -> movie != null)
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(movie -> view.showMovieDetail(movie));
    disposeBag.add(disposable);
  }

  @Override public void search(final String movieTitle) {
    // Load new one and populate it into view
    Disposable disposable = repository.loadPopularMovies(false)
        .flatMap(Flowable::fromIterable)
        .filter(movie -> movie.getTitle() != null)
        .filter(movie -> movie.getTitle().toLowerCase().contains(movieTitle.toLowerCase()))
        .toList()
        .toFlowable()
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
        .subscribe(questions -> {
          if (questions.isEmpty()) {
            // Clear old data in view
            view.clearMovies();
            // Show notification
            view.showEmptySearchResult();
          } else {
            // Update filtered data
            view.showMovies(questions);
          }
        });

    disposeBag.add(disposable);
  }

  /**
   * Updates view after loading data is completed successfully.
   */
  private void handleReturnedData(List<Movie> list) {
    view.stopLoadingIndicator();
    if (list != null && !list.isEmpty()) {
      view.showMovies(list);
    } else {
      view.showNoDataMessage();
    }
  }

  /**
   * Updates view if there is an error after loading data from repository.
   */
  private void handleError(Throwable error) {
    view.stopLoadingIndicator();
    view.showErrorMessage(error.getLocalizedMessage());
  }
}
