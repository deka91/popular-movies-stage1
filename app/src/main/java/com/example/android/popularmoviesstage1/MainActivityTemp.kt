package com.example.android.popularmoviesstage1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivityTemp : AppCompatActivity() {

    private var movieAdapter: MovieAdapter
    private var movieList: ArrayList<Movie> = ArrayList()

    init {
        movieAdapter = MovieAdapter(this, movieList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}