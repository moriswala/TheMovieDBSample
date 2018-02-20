package com.yakub.themoviedbsample.ui.movies;

import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yakub.themoviedbsample.R;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.ui.base.BaseRecyclerViewAdapter;
import com.yakub.themoviedbsample.util.NumberUtils;

import java.security.InvalidParameterException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

class MoviesAdapter extends BaseRecyclerViewAdapter<MoviesAdapter.MovieViewHolder> {
  private final RecyclerView mRecyclerView;
  private boolean isLoading;
  private int totalItemCount;
  private int lastVisibleItem;
  private int visibleThreshold = 5;

  class MovieViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgCoverImage) SimpleDraweeView imgCoverImage;
    @BindView(R.id.text_title) TextView titleText;
    @BindView(R.id.rattingBar) RatingBar rattingBar;
    @BindView(R.id.textVoteCount) TextView textVoteCount;

    public MovieViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  private List<Movie> moviesList;

  public MoviesAdapter(@NonNull List<Movie> questions, @NonNull RecyclerView recyclerView) {
    this.moviesList = questions;
    this.mRecyclerView = recyclerView;
//    this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
//    {
//      @Override
//      public void onScrolled(RecyclerView recyclerView, int dx, int dy)
//      {
//        super.onScrolled(recyclerView, dx, dy);
//
//        totalItemCount = recyclerView.getLayoutManager().getItemCount();
//        int lastVisibleItem = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
////        lastVisibleItem = getLastVisibleItem(lastItemsArray);
//
//        if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold))
//        {
//          if (onLoadMoreListener != null)
//          {
//            onLoadMoreListener.onLoadMore();
//          }
//          isLoading = true;
//        }
//      }
//
//      public int getLastVisibleItem(int[] lastVisibleItemPositions)
//      {
//        int maxSize = 0;
//        for (int i = 0; i < lastVisibleItemPositions.length; i++)
//        {
//          if (i == 0)
//          {
//            maxSize = lastVisibleItemPositions[i];
//          }
//          else if (lastVisibleItemPositions[i] > maxSize)
//          {
//            maxSize = lastVisibleItemPositions[i];
//          }
//        }
//        return maxSize;
//      }
//    });
  }

  public void setLoaded() {
    isLoading = false;
  }

  @Override public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_movie, viewGroup, false);
    return new MovieViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
    super.onBindViewHolder(viewHolder, i);
    MovieViewHolder vh = (MovieViewHolder) viewHolder; //safe cast
    Movie movie = moviesList.get(i);
    vh.titleText.setText(movie.getTitle());
    vh.rattingBar.setRating(movie.getAvgVote()/2);
    vh.textVoteCount.setText(NumberUtils.formatNumberInKMB(movie.getVoteCount())+" ");
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

  public void appendData(List<Movie> movies) {
//    this.moviesList.clear();
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
