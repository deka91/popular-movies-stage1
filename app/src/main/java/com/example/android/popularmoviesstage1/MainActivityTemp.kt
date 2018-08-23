package com.example.android.popularmoviesstage1

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.android.popularmoviesstage1.Constants.Companion.CATEGORY_POPULAR
import com.example.android.popularmoviesstage1.Constants.Companion.CATEGORY_TOP_RATED
import kotlinx.android.synthetic.main.activity_main.*


class MainActivityTemp : AppCompatActivity() {

    private var movieAdapter: MovieAdapter
    private var movieList: ArrayList<Movie> = ArrayList()

    init {
        movieAdapter = MovieAdapter(this, movieList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridview_main_movies.adapter = movieAdapter
        gridview_main_movies.setOnItemClickListener { parent, view, position, id ->
            var movie: Movie = movieAdapter.getItem(position)
            var intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            startActivity(intent)
        }

        showMovies(CATEGORY_POPULAR)
    }

    private fun showMovies(filter: String) {
        textview_main_error_message.visibility = View.INVISIBLE
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
        var id: Int = item!!.itemId

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