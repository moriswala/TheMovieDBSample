package com.yakub.themoviedbsample.ui.movies;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yakub.themoviedbsample.R;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.ui.base.BaseRecyclerViewAdapter;

import io.reactivex.annotations.NonNull;
import java.security.InvalidParameterException;
import java.util.List;

class MoviesAdapter extends BaseRecyclerViewAdapter<MoviesAdapter.QuestionViewHolder> {
  class QuestionViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgCoverImage) SimpleDraweeView imgCoverImage;
    @BindView(R.id.text_title) TextView titleText;
//    @BindView(R.id.text_user) TextView userText;
//    @BindView(R.id.text_created_time) TextView createdTimeText;
//    @BindView(R.id.image_profile) ImageView profileImage;

    public QuestionViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  private List<Movie> moviesList;

  public MoviesAdapter(@NonNull List<Movie> questions) {
    this.moviesList = questions;
  }

  @Override public QuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_question, viewGroup, false);
    return new QuestionViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    super.onBindViewHolder(viewHolder, i);
    QuestionViewHolder vh = (QuestionViewHolder) viewHolder; //safe cast
    Movie movie = moviesList.get(i);
    vh.titleText.setText(movie.getTitle());
//    vh.userText.setText(movie.getOverview());
//    vh.createdTimeText.setText(DateTimeUtils.formatRelativeTime(movie.getReleaseDate()));
//    Glide.with(vh.profileImage).load(movie.getBackdropPath()).into(vh.profileImage);
    Uri uri = Uri.parse("https://image.tmdb.org/t/p/w300/"+movie.getBackdropPath());
    vh.imgCoverImage.setImageURI(uri);
  }

  @Override public int getItemCount() {
    return moviesList.size();
  }

  public void replaceData(List<Movie> movies) {
    this.moviesList.clear();
    this.moviesList.addAll(movies);
    notifyDataSetChanged();
  }

  public Movie getItem(int position) {
    if (position < 0 || position >= moviesList.size()) {
      throw new InvalidParameterException("Invalid item index");
    }
    return moviesList.get(position);
  }

  public void clearData() {
    moviesList.clear();
    notifyDataSetChanged();
  }
}
