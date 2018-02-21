package com.yakub.themoviedbsample.data.api;

import com.google.gson.annotations.SerializedName;
import com.yakub.themoviedbsample.data.Config;
import com.yakub.themoviedbsample.data.model.Movie;

import java.util.List;

/**
 * Created by yakubmoriswala on 2/18/18.
 */

public class MovieListResponce {

    @SerializedName(Config.Params.page)
    private int page;

    @SerializedName(Config.Params.total_results)
    private int totalResults;

    @SerializedName(Config.Params.total_pages)
    private int totalPages;

    @SerializedName(Config.Params.results)
    private List<Movie> movies;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
