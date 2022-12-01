package com.elash.evalfinaland.services


import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {


    private val apiKey: String
        get() = "1289bdc96c0e3bf709e4789f7a01faf9"

    private val language: String
        get() = "en-US"

    private val page: Int
        get() = 1

    private val includeAdult: Boolean
        get() = false


    @Headers("Content-type: application/json")
    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String,
                             @Query("api_key", encoded = false) apiKey: String = this.apiKey,
                             @Query("language", encoded = false) language: String = this.language,
                             @Query("page", encoded = false) page: Int = this.page,
                             @Query("include_adult", encoded = false) includeAdult: Boolean = this.includeAdult
    ): Response<MovieModel>

    @Headers("Content-type: application/json")
    @GET("movie/{id}")
    suspend fun movie(@Path("id") id: Int,
                      @Query("api_key", encoded = false) apiKey: String = this.apiKey,
                      @Query("language", encoded = false) language: String = this.language
    ): Response<Movie>

    @Headers("Content-type: application/json")
    @GET("movie/{id}/similar")
    suspend fun similarMovies(@Path("id") id: Int,
                              @Query("api_key", encoded = false) apiKey: String = this.apiKey,
                              @Query("language", encoded = false) language: String = this.language
    ): Response<MovieModel>

    @Headers("Content-type: application/json")
    @GET("trending/all/day")
    suspend fun trendingMovies(@Query("api_key", encoded = false) apiKey: String = this.apiKey): Response<MovieModel>



}