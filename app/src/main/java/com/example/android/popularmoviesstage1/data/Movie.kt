package com.example.android.popularmoviesstage1.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Deniz Kalem on 09.08.2018.
 */
@Parcelize
data class Movie(var title: String, var posterPath: String, var overview: String, var voteAverage: String, var releaseDate: String) : Parcelable