package com.yakub.themoviedbsample.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.yakub.themoviedbsample.data.model.Convertors;
import com.yakub.themoviedbsample.data.model.Movie;

@Database(entities = Movie.class, version = 1)
@TypeConverters({Convertors.class})
public abstract class TheMovieDb extends RoomDatabase {

  public abstract MovieDao movieDao();
}
