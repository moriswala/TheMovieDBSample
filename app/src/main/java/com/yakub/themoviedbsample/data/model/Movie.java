package com.yakub.themoviedbsample.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.yakub.themoviedbsample.data.Config;

import java.util.List;
import java.util.Map;

/**
 * Created by yakubmoriswala on 2/18/18.
 */
@Entity(tableName = Config.MOVIE_TABLE_NAME)
public class Movie {

    @SerializedName(Config.Params.genre_ids)
    public List<Integer> generIds;

    @SerializedName(Config.Params.adult)
    private boolean adult;

    @SerializedName(Config.Params.backdrop_path)
    private String backdropPath;

    @SerializedName(Config.Params.belongs_to_collection)
    private String belongsToCollection;

    @SerializedName(Config.Params.budget)
    private long budget;

    @SerializedName(Config.Params.genres)
    private List<Map<String, String>> geners;

    @SerializedName(Config.Params.homepage)
    private String homepage;

    @PrimaryKey
    @SerializedName(Config.Params.id)
    private int id;

    @SerializedName(Config.Params.imdb_id)
    private String imdbId;

    @SerializedName(Config.Params.original_language)
    private String originalLang;

    @SerializedName(Config.Params.original_title)
    private String originalTitle;

    @SerializedName(Config.Params.overview)
    private String overview;

    @SerializedName(Config.Params.popularity)
    private double popularity;

    @SerializedName(Config.Params.poster_path)
    private String posterPath;

    @SerializedName(Config.Params.production_companies)
    private List<Map<String, String>> productionCompanies;

    @SerializedName(Config.Params.production_countries)
    private List<Map<String, String>> productionCountries;

    @SerializedName(Config.Params.release_date)
    private String releaseDate;

    @SerializedName(Config.Params.revenue)
    private long revenue;

    @SerializedName(Config.Params.runtime)
    private long runtime;

    @SerializedName(Config.Params.spoken_languages)
    private List<Map<String, String>> spockenLanguages;

    @SerializedName(Config.Params.status)
    private String status;

    @SerializedName(Config.Params.tagline)
    private String tagLine;

    @SerializedName(Config.Params.title)
    private String title;

    @SerializedName(Config.Params.video)
    private boolean video;

    @SerializedName(Config.Params.vote_average)
    private float avgVote;

    @SerializedName(Config.Params.vote_count)
    private int voteCount;


}
