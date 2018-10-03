package com.example.android.popularmoviesstage1.features.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.popularmoviesstage1.R
import com.example.android.popularmoviesstage1.data.Movie
import com.example.android.popularmoviesstage1.utils.Constants
import com.example.android.popularmoviesstage1.utils.Constants.Companion.PARCELABLE_EXTRA_MOVIE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

/**
 * Created by Deniz Kalem on 23.08.2018.
 */
class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movie: Movie = intent.getParcelableExtra(PARCELABLE_EXTRA_MOVIE)

        showMovieDetails(movie)
        loadPosterImage(movie.posterPath)
    }

    override fun showMovieDetails(movie: Movie) {
        textview_detail_title.text = movie.title
        textview_detail_release.text = getString(R.string.release, movie.releaseDate)
        textview_detail_rating.text = resources.getString(R.string.rating, movie.voteAverage)
        textview_detail_description.text = resources.getString(R.string.overview, movie.overview)
    }

    override fun loadPosterImage(path: String) {
        val urlBuilder = StringBuilder().append(Constants.MOVIE_URL).append(Constants.IMAGE_SIZE).append(path).toString()
        Picasso.with(applicationContext).load(urlBuilder).into(imageview_main_movieposter)
    }
}