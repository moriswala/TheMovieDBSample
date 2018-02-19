package com.yakub.themoviedbsample.data.api;

import com.yakub.themoviedbsample.data.Config;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.stackexchange.com/2.2/questions?site=stackoverflow&tagged=android
public interface MovieService {

//  Flowable<MovieListResponce> loadPopularMovies(@Query("tagged") String tag);

  @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
  Flowable<MovieListResponce> loadPopularMovies(@Query(Config.Params.API_KEY) String apiKey);

  @GET("movie/top_rated?language=en-US&page=1")
  Flowable<MovieListResponce> loadTopRatedMovies(@Query(Config.Params.API_KEY) String apiKey);

  @GET("search/movie?language=en-US&page=1&include_adult=false")
  Flowable<MovieListResponce> searchMovie(@Query(Config.Params.API_KEY) String apiKey,
                                          @Query(Config.Params.query) String queryText);
}
