package com.example.android.popularmoviesstage1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.example.android.popularmoviesstage1.R
import com.example.android.popularmoviesstage1.model.Movie
import com.example.android.popularmoviesstage1.utils.Constants.Companion.BASE_URL
import com.example.android.popularmoviesstage1.utils.Constants.Companion.IMAGE_SIZE
import com.squareup.picasso.Picasso

/**
 * Created by Deniz Kalem on 12.08.2018.
 */
class PosterAdapter(context: Context, movies: List<Movie>) : BaseAdapter() {

    private val context: Context = context
    private val movies: MutableList<Movie> = movies as MutableList<Movie>

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView?

        if (convertView == null) {
            val inflater = parent!!.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            imageView = inflater.inflate(R.layout.movie_item, parent, false) as ImageView
        } else {
            imageView = convertView as ImageView
        }

        val url: String = StringBuilder().append(BASE_URL).append(IMAGE_SIZE).append(getItem(position).posterPath.trim()).toString()

        Picasso.with(context).load(url).into(imageView)

        return imageView
    }

    override fun getItem(position: Int): Movie {
        return movies.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movies.size
    }

    fun clear() {
        if (movies.size > 0)
            movies.clear()
    }
}