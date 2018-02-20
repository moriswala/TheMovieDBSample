package com.yakub.themoviedbsample.ui.moviedetail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.yakub.themoviedbsample.data.repository.MoviesRepository;
import com.yakub.themoviedbsample.util.schedulers.RunOn;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

import static com.yakub.themoviedbsample.util.schedulers.SchedulerType.IO;
import static com.yakub.themoviedbsample.util.schedulers.SchedulerType.UI;

/**
 * A presenter with life-cycle aware.
 */
public class MovieDetailPresenter implements MovieDetailContract.Presenter, LifecycleObserver {

  private MoviesRepository repository;

  private MovieDetailContract.View view;

  private Scheduler ioScheduler;
  private Scheduler uiScheduler;

  private CompositeDisposable disposeBag;

  @Inject public MovieDetailPresenter(MoviesRepository repository, MovieDetailContract.View view,
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

  }

  @Override @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE) public void onDetach() {
    // Clean up any no-longer-use resources here
    disposeBag.clear();
  }

}
