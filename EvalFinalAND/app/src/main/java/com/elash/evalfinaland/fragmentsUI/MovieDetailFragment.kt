package com.elash.evalfinaland.fragmentsUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elash.evalfinaland.adapters.SimilarMoviesRowAdapter
import com.elash.evalfinaland.databinding.FragmentMovieDetailBinding
import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.services.MovieServiceImpl
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.RoundingMode
import java.text.DecimalFormat


class MovieDetailFragment : Fragment(), SimilarMoviesRowAdapter.ClickListener {


    private var binding: FragmentMovieDetailBinding? = null
    private val movieService by lazy { MovieServiceImpl() }
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        similarMovies(args.movieId)
        movieDetail(args.movieId)
        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        return binding?.root




    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.backButton?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun movieDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.movie(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        val df = DecimalFormat("#.#")
                        df.roundingMode = RoundingMode.CEILING
                        binding?.movieTitle?.text = it.title
                        binding?.movieRating?.text = df.format(it.voteAverage).toString()
                        binding?.movieSynopsis?.text = it.overview

                        if (!it.posterPath.isNullOrEmpty()) {
                            val imageUrl = "https://image.tmdb.org/t/p/w500${it.posterPath}"
                            Picasso.get()
                                .load(imageUrl)
                                .into(binding?.movieImg)
                        }

                        if (!it.backdropPath.isNullOrEmpty()) {
                            val imageUrl = "https://image.tmdb.org/t/p/w500${it.backdropPath}"
                            Picasso.get()
                                .load(imageUrl)
                                .into(binding?.movieBackImg)
                        }
                    }
                }
            }
        }
    }

    private fun similarMovies(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieService.similarMovies(id)
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.results?.let {
                        setUpRecyclerView(it)
                    }
                }
            }
        }
    }


    private fun setUpRecyclerView(data: List<Movie>) {
        val recyclerView = binding?.similarMovieRecyclerVue
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        recyclerView?.adapter = SimilarMoviesRowAdapter(data, this)

    }

    override fun clickListener(movie: Movie) {
        movieDetail(movie.id)
        similarMovies(movie.id)
    }



}