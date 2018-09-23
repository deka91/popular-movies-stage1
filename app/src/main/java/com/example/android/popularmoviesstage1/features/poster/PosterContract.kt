package com.example.android.popularmoviesstage1.features.poster

import com.example.android.popularmoviesstage1.model.Movie

/**
 * Created by Deniz Kalem on 22.09.2018.
 */
interface PosterContract {

    interface View {
        fun showPoster(filter: String);
        fun updatePoster(movies: Array<Movie?>)
    }

    interface Presenter {
        fun getMoviesFromJson(jsonStringMovie: String?): Array<Movie?>
    }
}