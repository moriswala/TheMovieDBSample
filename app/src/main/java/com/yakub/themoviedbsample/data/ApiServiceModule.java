package com.yakub.themoviedbsample.data;

import com.yakub.themoviedbsample.data.api.HeaderInterceptor;
import com.yakub.themoviedbsample.data.api.QuestionService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class ApiServiceModule {

  /**
   *
   * Get configurations :
   * https://developers.themoviedb.org/3/configuration/get-api-configuration
   *
   * https://api.themoviedb.org/3/configuration?api_key=6177565665ecbc9cc6b09fbe70f78fec
   *
   * ex: https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
   *---------------------------------------------------------------
   * popularity by desc :
   * https://developers.themoviedb.org/3/discover/movie-discover
   *
   * https://api.themoviedb.org/3/discover/movie?api_key=6177565665ecbc9cc6b09fbe70f78fec
   * &language=en-US
   * &sort_by=popularity.desc
   * &include_adult=false
   * &include_video=false
   * &page=1
   *---------------------------------------------------------------------------------
   * top rated movie:
   * https://developers.themoviedb.org/3/movies/get-top-rated-movies
   *
   * https://api.themoviedb.org/3/movie/top_rated?api_key=6177565665ecbc9cc6b09fbe70f78fec
   * &language=en-US
   * &page=1
   *
   * -----------------------------------------------------------------------
   * search movies :
   * https://developers.themoviedb.org/3/search/search-movies
   *
   * https://api.themoviedb.org/3/search/movie?api_key=6177565665ecbc9cc6b09fbe70f78fec
   * &language=en-US
   * &query=transformer
   * &page=1&
   * include_adult=false
   *
   *-------------------------------------------------------------------------
   * movie detail :
   * https://developers.themoviedb.org/3/movies/get-movie-details
   *
   * https://api.themoviedb.org/3/movie/45340?api_key=6177565665ecbc9cc6b09fbe70f78fec
   * &language=en-US
   */
  private static final String BASE_URL = "base_url";

  @Provides
  @Named(BASE_URL)
  String provideBaseUrl() {
    return Config.API_HOST;
  }

  @Provides
  @Singleton
  HeaderInterceptor provideHeaderInterceptor() {
    return new HeaderInterceptor();
  }

  @Provides
  @Singleton
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
  }

  @Provides
  @Singleton
  OkHttpClient provideHttpClient(HeaderInterceptor headerInterceptor,
      HttpLoggingInterceptor httpInterceptor) {
    return new OkHttpClient.Builder().addInterceptor(headerInterceptor)
        .addInterceptor(httpInterceptor)
        .build();
  }

  @Provides
  @Singleton
  Converter.Factory provideGsonConverterFactory() {
    return GsonConverterFactory.create();
  }

  @Provides
  @Singleton
  CallAdapter.Factory provideRxJavaAdapterFactory() {
    return RxJava2CallAdapterFactory.create();
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(@Named(BASE_URL) String baseUrl, Converter.Factory converterFactory,
      CallAdapter.Factory callAdapterFactory, OkHttpClient client) {
    return new Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(client)
        .build();
  }

  @Provides
  @Singleton
  QuestionService provideQuestionService(Retrofit retrofit) {
    return retrofit.create(QuestionService.class);
  }
}
