package com.elash.evalfinaland.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.databinding.SearchMovieCellBinding

import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SearchMovieRowAdapter(private val movies: List<Movie>, private val listener: ClickListener) : RecyclerView.Adapter<SearchMovieRowAdapter.MoviesRowHolder>() {
    interface ClickListener{
        fun clickListener(movie: Movie)
    }


    private lateinit var binding: SearchMovieCellBinding

    class MoviesRowHolder(private var binding: SearchMovieCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: ClickListener) {

            binding.movieReleaseDate.text = movie.releaseDate
            binding.movieTitle.text = movie.title
            binding.movieReleaseDate.text = movie.releaseDate

            if (!movie.posterPath.isNullOrEmpty()) {
                val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
                Picasso.get()
                    .load(imageUrl)
                    .into(binding.movieImg)
            }
            itemView.setOnClickListener {
                listener.clickListener(movie)
            }

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesRowHolder {
        binding = SearchMovieCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MoviesRowHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesRowHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }
}