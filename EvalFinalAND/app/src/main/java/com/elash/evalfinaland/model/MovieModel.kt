package com.elash.evalfinaland.model

import com.google.gson.annotations.SerializedName


data class MovieModel(
    val results: List<Movie>?,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int


)


data class Movie (
    @SerializedName("vote_average")
    val voteAverage: Float,
    val id : Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    var title: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val overview: String,
    @SerializedName("backdrop_path")
    val backdropPath: String?
)