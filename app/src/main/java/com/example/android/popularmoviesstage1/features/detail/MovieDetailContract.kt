package com.example.android.popularmoviesstage1.features.detail

import com.example.android.popularmoviesstage1.data.Movie

/**
 * Created by Deniz Kalem on 03.10.2018.
 */
interface MovieDetailContract {

    interface View {
        fun showMovieDetails(movie: Movie)
        fun loadPosterImage(path: String)
    }
}