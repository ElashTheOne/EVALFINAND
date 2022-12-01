package com.elash.evalfinaland.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.databinding.TrendingCellBinding
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat

class TrendingMoviesRowAdapter(private val movies: List<Movie>, private val listener: ClickListener): RecyclerView.Adapter<TrendingMoviesRowAdapter.MoviesRowHolder>() {
    interface ClickListener{
        fun clickListener(movie: Movie)
    }
    private lateinit var binding: TrendingCellBinding

    class MoviesRowHolder(private var binding: TrendingCellBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, listener: ClickListener){
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING

            binding.movieRating.text = df.format(movie.voteAverage).toString()
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
        binding = TrendingCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesRowHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesRowHolder, position: Int) {
        holder.bind(movies[position], listener)
    }

    override fun getItemCount(): Int {
        return movies.count()
    }
}