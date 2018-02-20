package com.yakub.themoviedbsample.ui.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;
import com.yakub.themoviedbsample.AndroidApplication;
import com.yakub.themoviedbsample.data.MoviesRepositoryComponent;

public class BaseActivity extends AppCompatActivity {
  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

  protected MoviesRepositoryComponent getMovieRepositoryComponent() {
    return ((AndroidApplication) getApplication()).getMoviesRepositoryComponent();
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }
}
