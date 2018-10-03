package com.example.android.popularmoviesstage1.features.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.popularmoviesstage1.R
import com.example.android.popularmoviesstage1.data.Movie
import com.example.android.popularmoviesstage1.utils.Constants.Companion.IMAGE_SIZE
import com.example.android.popularmoviesstage1.utils.Constants.Companion.MOVIE_URL
import com.example.android.popularmoviesstage1.utils.Constants.Companion.PARCELABLE_EXTRA_MOVIE
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*

/**
 * Created by Deniz Kalem on 23.08.2018.
 */
class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movies: Movie = intent.getParcelableExtra(PARCELABLE_EXTRA_MOVIE)
        textview_detail_title.text = movies.title
        textview_detail_release.text = getString(R.string.release, movies.releaseDate)
        textview_detail_rating.text = resources.getString(R.string.rating, movies.voteAverage)
        textview_detail_description.text = resources.getString(R.string.overview, movies.overview)

        loadPoster(movies.posterPath)
    }

    private fun loadPoster(path: String) {
        val urlBuilder = StringBuilder().append(MOVIE_URL).append(IMAGE_SIZE).append(path).toString()
        Picasso.with(applicationContext).load(urlBuilder).into(imageview_main_movieposter)
    }
}