package com.yakub.themoviedbsample;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.yakub.themoviedbsample.data.DaggerMoviesRepositoryComponent;
import com.yakub.themoviedbsample.data.MoviesRepositoryComponent;

import timber.log.Timber;

public class AndroidApplication extends Application {

  private MoviesRepositoryComponent repositoryComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    initializeDependencies();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
      Stetho.initializeWithDefaults(this);
    }

    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }
    LeakCanary.install(this);
  }

  private void initializeDependencies() {
    repositoryComponent = DaggerMoviesRepositoryComponent.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public MoviesRepositoryComponent getMoviesRepositoryComponent() {
    return repositoryComponent;
  }
}
