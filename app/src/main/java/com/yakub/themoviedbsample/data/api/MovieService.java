package com.yakub.themoviedbsample.data.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

//https://api.stackexchange.com/2.2/questions?site=stackoverflow&tagged=android
public interface MovieService {

//  Flowable<MovieListResponce> loadPopularMovies(@Query("tagged") String tag);

  @GET("discover/movie?language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1")
  Flowable<MovieListResponce> loadPopularMovies(@Query("api_key") String apiKey);



}
