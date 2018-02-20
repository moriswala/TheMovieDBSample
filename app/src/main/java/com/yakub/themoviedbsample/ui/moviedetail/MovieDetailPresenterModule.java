package com.yakub.themoviedbsample.ui.moviedetail;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 2/20/2018.
 */
@Module
public class MovieDetailPresenterModule {
    private MovieDetailContract.View view;

    public MovieDetailPresenterModule(MovieDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public MovieDetailContract.View provideView() {
        return view;
    }
}
