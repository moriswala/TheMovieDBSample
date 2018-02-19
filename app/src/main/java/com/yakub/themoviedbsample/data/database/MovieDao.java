package com.yakub.themoviedbsample.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.model.Movie;
import com.yakub.themoviedbsample.data.model.Question;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yakubmoriswala on 2/19/18.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM " + Config.MOVIE_TABLE_NAME)
    Flowable<List<Movie>> getAllMovies();

    @Query("SELECT * FROM " + Config.MOVIE_TABLE_NAME + " WHERE id == :id")
    Flowable<Movie> getQuestionById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie question);

    @Query("DELETE FROM " + Config.MOVIE_TABLE_NAME)
    void deleteAll();
}
