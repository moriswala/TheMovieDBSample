package com.yakub.themoviedbsample.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.yakub.themoviedbsample.data.database.MovieDao;
import com.yakub.themoviedbsample.data.database.TheMovieDb;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
  private static final String DATABASE = "database_name";

  @Provides
  @Named(DATABASE)
  String provideDatabaseName() {
    return Config.DATABASE_NAME;
  }

  @Provides
  @Singleton
  TheMovieDb provideStackOverflowDao(Context context, @Named(DATABASE) String databaseName) {
    return Room.databaseBuilder(context, TheMovieDb.class, databaseName).build();
  }

  @Provides
  @Singleton
  MovieDao provideQuestionDao(TheMovieDb theMovieDb) {
    return theMovieDb.movieDao();
  }
}
