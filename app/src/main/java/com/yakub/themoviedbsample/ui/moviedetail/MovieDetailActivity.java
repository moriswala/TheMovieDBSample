package com.yakub.themoviedbsample.ui.moviedetail;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yakub.themoviedbsample.R;
import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.View {
  @BindView(R.id.imgCoverImage) SimpleDraweeView imgCoverImage;
  @BindView(R.id.text_overview) TextView textOverview;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    ButterKnife.bind(this);
    initializePresenter();
    Movie movie = getIntentParams();
    if(movie!=null) {
      setTitle(movie.getTitle());
//      getActionBar().setDisplayHomeAsUpEnabled(true);
      setupWidgets(movie);
    }
  }

  private Movie getIntentParams() {
    if(getIntent().hasExtra(Config.Extra.MOVIE)){
      Movie movie = (Movie) getIntent().getSerializableExtra(Config.Extra.MOVIE);
      return movie;
    }
    return null;
  }

  private void initializePresenter() {
    DaggerMovieDetailComponent.builder()
        .movieDetailPresenterModule(new MovieDetailPresenterModule(this))
        .moviesRepositoryComponent(getMovieRepositoryComponent())
        .build()
        .inject(this);
  }

  private void setupWidgets(Movie movie) {

    textOverview.setText(movie.getOverview());
    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w500/"+movie.getPosterPath());
    imgCoverImage.setImageURI(uri);
  }


  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.movie_detail, menu);
    return true;
  }

  boolean isStarred = false;
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.menuStar:
        if(isStarred = !isStarred)
          item.setIcon(R.drawable.ic_star_white);
        else
          item.setIcon(R.drawable.ic_star_border_white);
        return true;

    }
    return super.onOptionsItemSelected(item);
  }

}
