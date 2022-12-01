package com.elash.evalfinaland.services


import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.model.MovieModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MovieServiceImpl: MovieService {


    private val baseUrl = "https://api.themoviedb.org/3/"

    private fun getRetrofit(): Retrofit {



        val okBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(100, TimeUnit.SECONDS)
            callTimeout(100, TimeUnit.SECONDS)
            readTimeout (100, TimeUnit.SECONDS)
            writeTimeout(100, TimeUnit.SECONDS)
        }
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okBuilder.build())
            .build()
    }

    override suspend fun searchMovies(
        query: String,
        apiKey: String,
        language: String,
        page: Int,
        includeAdult: Boolean
    ): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).searchMovies(query)
    }

    override suspend fun movie(id: Int, apiKey: String, language: String): Response<Movie> {
        return getRetrofit().create(MovieService::class.java).movie(id)
    }


    override suspend fun similarMovies(
        id: Int,
        apiKey: String,
        language: String
    ): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).similarMovies(id)
    }


    override suspend fun trendingMovies(apiKey: String): Response<MovieModel> {
        return getRetrofit().create(MovieService::class.java).trendingMovies()
    }


}