package com.example.android.popularmoviesstage1

/**
 * Created by Deniz Kalem on 15.08.2018.
 */
class Constants {
    companion object {
        const val BASE_URL = "https://image.tmdb.org/t/p/"
        const val MOVIE_URL = "https://api.themoviedb.org/3/movie/"
        const val IMAGE_SIZE = "w500"
        const val CATEGORY_POPULAR : String = "popular"
        const val CATEGORY_TOP_RATED : String = "top_rated"
        const val PARCELABLE_EXTRA_MOVIE : String = "movie"
        const val QUERY_PARAMETER_API : String = "api_key"
        const val REQUEST_METHOD : String = "GET"
        const val MOVIE_JSON_RESULTS : String = "results"
        const val MOVIE_JSON_ORIGINAL_TITLE : String = "original_title"
        const val MOVIE_JSON_POSTER_PATH : String = "poster_path"
        const val MOVIE_JSON_OVERVIEW : String = "overview"
        const val MOVIE_JSON_VOTE_AVERAGE : String = "vote_average"
        const val MOVIE_JSON_RELEASE_DATE : String = "release_date"
    }
}