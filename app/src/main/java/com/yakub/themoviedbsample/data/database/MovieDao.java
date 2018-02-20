package com.yakub.themoviedbsample.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.model.Movie;

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
    Flowable<Movie> getMovieById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("DELETE FROM " + Config.MOVIE_TABLE_NAME)
    void deleteAll();

    @Query("SELECT * FROM " + Config.MOVIE_TABLE_NAME + " ORDER BY "+Config.Params.popularity + " DESC LIMIT :page")
    Flowable<List<Movie>> getAllPopularMovies(int page);

    @Query("SELECT * FROM " + Config.MOVIE_TABLE_NAME + " ORDER BY avgVote"+ " DESC")
    Flowable<List<Movie>> getAllTopRatedMovies();

    @Query("SELECT * FROM " + Config.MOVIE_TABLE_NAME + " WHERE "+Config.Params.title+" like :queryText")
    Flowable<List<Movie>> searchMovie(String queryText);

}
