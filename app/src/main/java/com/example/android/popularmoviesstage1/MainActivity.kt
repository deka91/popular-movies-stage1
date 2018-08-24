package com.example.android.popularmoviesstage1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.android.popularmoviesstage1.Constants.Companion.CATEGORY_POPULAR
import com.example.android.popularmoviesstage1.Constants.Companion.CATEGORY_TOP_RATED
import com.example.android.popularmoviesstage1.Constants.Companion.MOVIE_URL
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class MainActivity : AppCompatActivity() {

    private var movieAdapter: MovieAdapter
    private var movieList: ArrayList<Movie> = ArrayList()
    val API_KEY = "4fb159ec4eb6feeb16c5fb8db540cfd8"

    init {
        movieAdapter = MovieAdapter(this, movieList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridview_main_movies.adapter = movieAdapter
        gridview_main_movies.setOnItemClickListener { parent, view, position, id ->
            val movie: Movie = movieAdapter.getItem(position)
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }

        showMovies(CATEGORY_POPULAR)
    }

    private fun showMovies(filter: String) {
        textview_main_error_message.visibility = View.INVISIBLE

        doAsync {
            if (filter.isEmpty()) {
                return@doAsync
            }

            var urlConnection: HttpURLConnection? = null
            var movieJsonString: String? = null
            var reader: BufferedReader? = null

            val uri = Uri.parse(MOVIE_URL).buildUpon()
                    .appendEncodedPath(filter)
                    .appendQueryParameter("api_key", API_KEY)
                    .build()

            try {
                val url = URL(uri.toString())
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
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

            val movies = getMoviesFromJson(movieJsonString)

            uiThread() {
                progressbar_main_loading_indicator.visibility = View.INVISIBLE
                if (movies != null) {
                    updateAdapter(movies)
                } else {
                    textview_main_error_message.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateAdapter(movies: Array<Movie?>) {
        movieAdapter.clear()
        Collections.addAll<Movie>(movieList, *movies)
        movieAdapter.notifyDataSetChanged()
    }

    @Throws(JSONException::class)
    fun getMoviesFromJson(jsonStringMovie: String?): Array<Movie?> {
        if (jsonStringMovie == null || "" == jsonStringMovie) {
            return emptyArray()
        }

        val jsonObjectMovie = JSONObject(jsonStringMovie)
        val jsonArrayMovies = jsonObjectMovie.getJSONArray("results")

        val movies = arrayOfNulls<Movie>(jsonArrayMovies.length())

        for (i in 0 until jsonArrayMovies.length()) {
            val `object` = jsonArrayMovies.getJSONObject(i)
            movies[i] = Movie(`object`.getString("original_title"),
                    `object`.getString("poster_path"),
                    `object`.getString("overview"),
                    `object`.getString("vote_average"),
                    `object`.getString("release_date"))
        }
        return movies
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        showMovies(CATEGORY_POPULAR)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id: Int = item!!.itemId

        if (id == R.id.action_top_rated) {
            showMovies(CATEGORY_TOP_RATED)
            return true;
        }

        if (id == R.id.action_most_popular || id == R.id.action_refresh) {
            showMovies(CATEGORY_POPULAR)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}