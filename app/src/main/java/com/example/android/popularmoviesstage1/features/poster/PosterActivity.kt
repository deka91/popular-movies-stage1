package com.example.android.popularmoviesstage1.features.poster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.android.popularmoviesstage1.BuildConfig
import com.example.android.popularmoviesstage1.R
import com.example.android.popularmoviesstage1.adapter.PosterAdapter
import com.example.android.popularmoviesstage1.data.Movie
import com.example.android.popularmoviesstage1.features.detail.MovieDetailActivity
import com.example.android.popularmoviesstage1.utils.Constants
import com.example.android.popularmoviesstage1.utils.Constants.Companion.CATEGORY_POPULAR
import com.example.android.popularmoviesstage1.utils.Constants.Companion.PARCELABLE_EXTRA_MOVIE
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by Deniz Kalem on 22.08.2018.
 */
class PosterActivity : AppCompatActivity(), PosterContract.View {

    private var mPosterAdapter: PosterAdapter
    private var movieList: ArrayList<Movie> = ArrayList()
    private var presenter: PosterPrenter? = null

    init {
        mPosterAdapter = PosterAdapter(this, movieList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = PosterPrenter(this)

        initAdapter()
        showPoster(CATEGORY_POPULAR)
    }

    override fun onStart() {
        super.onStart()
        showPoster(CATEGORY_POPULAR)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id: Int = item!!.itemId

        if (id == R.id.action_top_rated) {
            showPoster(Constants.CATEGORY_TOP_RATED)
            return true;
        }

        if (id == R.id.action_most_popular || id == R.id.action_refresh) {
            showPoster(CATEGORY_POPULAR)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showPoster(filter: String) {
        textview_main_error_message.visibility = View.INVISIBLE

        doAsync {
            if (filter.isEmpty()) {
                return@doAsync
            }

            var urlConnection: HttpURLConnection? = null
            var movieJsonString: String? = null
            var reader: BufferedReader? = null

            val uri = Uri.parse(Constants.MOVIE_URL).buildUpon()
                    .appendEncodedPath(filter)
                    .appendQueryParameter(Constants.QUERY_PARAMETER_API, BuildConfig.Api_Key)
                    .build()

            try {
                val url = URL(uri.toString())
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = Constants.REQUEST_METHOD
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val builder = StringBuilder()
                if (inputStream == null) {
                    return@doAsync
                }
                reader = BufferedReader(InputStreamReader(inputStream!!))

                while (true) {
                    val line = reader.readLine() ?: break
                    builder.append(line + "\n")
                }

                if (builder.isEmpty()) {
                    return@doAsync
                }

                movieJsonString = builder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()

                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            val movies = presenter!!.getMoviesFromJson(movieJsonString)

            uiThread() {
                progressbar_main_loading_indicator.visibility = View.INVISIBLE
                if (movies != null) {
                    updatePoster(movies)
                } else {
                    textview_main_error_message.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun updatePoster(movies: Array<Movie?>) {
        mPosterAdapter.clear()
        Collections.addAll<Movie>(movieList, *movies)
        mPosterAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        gridview_main_movies.adapter = mPosterAdapter
        gridview_main_movies.setOnItemClickListener { parent, view, position, id ->
            val movie: Movie = mPosterAdapter.getItem(position)
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(PARCELABLE_EXTRA_MOVIE, movie)
            startActivity(intent)
        }
    }
}