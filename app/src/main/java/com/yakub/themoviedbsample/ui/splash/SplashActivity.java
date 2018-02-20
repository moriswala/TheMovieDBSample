package com.yakub.themoviedbsample.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yakub.themoviedbsample.R;
import com.yakub.themoviedbsample.ui.base.BaseActivity;
import com.yakub.themoviedbsample.ui.moviedetail.DaggerMovieDetailComponent;
import com.yakub.themoviedbsample.ui.movies.MoviesActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {

  Handler handler = new Handler();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    initializePresenter();
    handler.postDelayed(new Runnable(){
      @Override
      public void run(){
        SplashActivity.this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            startActivity(new Intent(SplashActivity.this, MoviesActivity.class));
            finish();
          }
        });
      }
    }, 3000);
  }

  private void initializePresenter() {
    DaggerSplashComponent.builder()
        .splashPresenterModule(new SplashPresenterModule(this))
        .moviesRepositoryComponent(getMovieRepositoryComponent())
        .build()
        .inject(this);
  }

}
