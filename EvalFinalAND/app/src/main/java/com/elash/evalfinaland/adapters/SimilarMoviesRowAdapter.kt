package com.elash.evalfinaland.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.databinding.SimilarMoviesCellBinding
import com.squareup.picasso.Picasso

class SimilarMoviesRowAdapter(private val movies: List<Movie>, private val listener: ClickListener): RecyclerView.Adapter<SimilarMoviesRowAdapter.MoviesRowHolder>() {
    interface ClickListener{
        fun clickListener(movie: Movie)
    }
    private lateinit var binding: SimilarMoviesCellBinding

    class MoviesRowHolder(private var binding: SimilarMoviesCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: ClickListener){
            binding.movieTitle.text = movie.title

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
        binding = SimilarMoviesCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesRowHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesRowHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }
}