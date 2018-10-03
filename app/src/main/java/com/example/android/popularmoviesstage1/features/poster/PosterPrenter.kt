package com.example.android.popularmoviesstage1.features.poster

import com.example.android.popularmoviesstage1.data.Movie
import com.example.android.popularmoviesstage1.utils.Constants
import org.json.JSONObject

/**
 * Created by Deniz Kalem on 22.09.2018.
 */
class PosterPrenter(_view: PosterContract.View) : PosterContract.Presenter {

    override fun getMoviesFromJson(jsonStringMovie: String?): Array<Movie?> {
        if (jsonStringMovie == null || "" == jsonStringMovie) {
            return emptyArray()
        }

        val jsonObjectMovie = JSONObject(jsonStringMovie)
        val jsonArrayMovies = jsonObjectMovie.getJSONArray(Constants.MOVIE_JSON_RESULTS)

        val movies = arrayOfNulls<Movie>(jsonArrayMovies.length())

        for (i in 0 until jsonArrayMovies.length()) {
            val `object` = jsonArrayMovies.getJSONObject(i)
            movies[i] = Movie(`object`.getString(Constants.MOVIE_JSON_ORIGINAL_TITLE),
                    `object`.getString(Constants.MOVIE_JSON_POSTER_PATH),
                    `object`.getString(Constants.MOVIE_JSON_OVERVIEW),
                    `object`.getString(Constants.MOVIE_JSON_VOTE_AVERAGE),
                    `object`.getString(Constants.MOVIE_JSON_RELEASE_DATE))
        }
        return movies
    }
}