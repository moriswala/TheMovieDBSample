package com.yakub.themoviedbsample.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.yakub.themoviedbsample.data.Config;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yakubmoriswala on 2/18/18.
 */
@Entity(tableName = Config.MOVIE_TABLE_NAME)
public class Movie implements Serializable{

    @SerializedName(Config.Params.genre_ids)
    public ArrayList<Integer> generIds;

    @SerializedName(Config.Params.adult)
    private boolean adult;

    @SerializedName(Config.Params.backdrop_path)
    private String backdropPath;

//    @SerializedName(Config.Params.belongs_to_collection)
//    private String belongsToCollection;

    @SerializedName(Config.Params.budget)
    private long budget;

//    @SerializedName(Config.Params.genres)
//    private ArrayList<HashMap<String, String>> geners;

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
    private float popularity;

    @SerializedName(Config.Params.poster_path)
    private String posterPath;

//    @SerializedName(Config.Params.production_companies)
//    private ArrayList<HashMap<String, String>> productionCompanies;
//
//    @SerializedName(Config.Params.production_countries)
//    private ArrayList<HashMap<String, String>> productionCountries;

    @SerializedName(Config.Params.release_date)
    private String releaseDate;

    @SerializedName(Config.Params.revenue)
    private long revenue;

    @SerializedName(Config.Params.runtime)
    private long runtime;

//    @SerializedName(Config.Params.spoken_languages)
//    private ArrayList<HashMap<String, String>> spockenLanguages;

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
    private long voteCount;

    public ArrayList<Integer> getGenerIds() {
        return generIds;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

//    public String getBelongsToCollection() {
//        return belongsToCollection;
//    }

    public long getBudget() {
        return budget;
    }

//    public ArrayList<HashMap<String, String>> getGeners() {
//        return geners;
//    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLang() {
        return originalLang;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

//    public ArrayList<HashMap<String, String>> getProductionCompanies() {
//        return productionCompanies;
//    }
//
//    public ArrayList<HashMap<String, String>> getProductionCountries() {
//        return productionCountries;
//    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public long getRuntime() {
        return runtime;
    }

//    public ArrayList<HashMap<String, String>> getSpockenLanguages() {
//        return spockenLanguages;
//    }

    public String getStatus() {
        return status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public float getAvgVote() {
        return avgVote;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGenerIds(ArrayList<Integer> generIds) {
        this.generIds = generIds;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

//    public void setBelongsToCollection(String belongsToCollection) {
//        this.belongsToCollection = belongsToCollection;
//    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setOriginalLang(String originalLang) {
        this.originalLang = originalLang;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setAvgVote(float avgVote) {
        this.avgVote = avgVote;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }
}
