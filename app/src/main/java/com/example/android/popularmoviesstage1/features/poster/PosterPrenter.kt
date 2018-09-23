package com.example.android.popularmoviesstage1.features.poster

import com.example.android.popularmoviesstage1.model.Movie

/**
 * Created by Deniz Kalem on 22.09.2018.
 */
class PosterPrenter(_view: PosterContract.View) : PosterContract.Presenter {
    private var view: PosterContract.View = _view

    init {
        view.showPoster()
    }

    override fun getMoviesFromJson(jsonStringMovie: String?): Array<Movie?> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}