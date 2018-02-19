package com.yakub.themoviedbsample.data.api;

import com.google.gson.annotations.SerializedName;
import com.yakub.themoviedbsample.data.model.Image;

import java.util.List;

/**
 * Created by yakubmoriswala on 2/18/18.
 */

//https://api.themoviedb.org/3/configuration?api_key=6177565665ecbc9cc6b09fbe70f78fec
public class ConfigurationResponse {

    @SerializedName("images")
    private Image images;

}
