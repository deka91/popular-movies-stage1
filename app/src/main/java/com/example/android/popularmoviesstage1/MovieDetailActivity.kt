package com.example.android.popularmoviesstage1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.android.popularmoviesstage1.Constants.Companion.IMAGE_SIZE
import com.example.android.popularmoviesstage1.Constants.Companion.PARCELABLE_EXTRA_MOVIE
import com.example.android.popularmoviesstage1.Constants.Companion.MOVIE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_detail.*
import java.lang.StringBuilder

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val movies: Movie = intent.getParcelableExtra(PARCELABLE_EXTRA_MOVIE)
        tv_title.text = movies.title
        tv_release.text = getString(R.string.release, movies.releaseDate)
        tv_rating.text = resources.getString(R.string.rating, movies.voteAverage)
        tv_description.text = resources.getString(R.string.overview, movies.overview)

        loadPoster(movies.posterPath)
    }

    private fun loadPoster(path: String) {
        val urlBuilder = StringBuilder().append(MOVIE_URL).append(IMAGE_SIZE).append(path).toString()
        Picasso.with(applicationContext).load(urlBuilder).into(iv_poster)
    }
}