package com.yakub.themoviedbsample.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yakubmoriswala on 2/19/18.
 */

public class Image {

    @SerializedName("base_url")
    public String baseUrl;

    @SerializedName("secure_base_url")
    public String secureBaseUrl;

    @SerializedName("backdrop_sizes")
    public List<String> backdropSizes;

    @SerializedName("logo_sizes")
    public List<String> logoSizes;

    @SerializedName("poster_sizes")
    public List<String> posterSizes;

    @SerializedName("profile_sizes")
    public List<String> profileSizes;

    @SerializedName("still_sizes")
    public List<String> stillSizes;
}
