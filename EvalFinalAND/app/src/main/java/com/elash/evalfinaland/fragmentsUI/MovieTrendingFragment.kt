package com.elash.evalfinaland.fragmentsUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elash.evalfinaland.adapters.TrendingMoviesRowAdapter
import com.elash.evalfinaland.databinding.FragmentTrendingBinding
import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.services.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieTrendingFragment : Fragment(), TrendingMoviesRowAdapter.ClickListener {

    private var binding: FragmentTrendingBinding? = null
    private val movieService by lazy { MovieServiceImpl() }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrendingBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trendingMovies()
    }

    private fun trendingMovies() {

            CoroutineScope(Dispatchers.IO).launch {
                val response = movieService.trendingMovies()
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
        val recyclerView = binding?.trendingRecyclerView
        recyclerView?.layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = TrendingMoviesRowAdapter(data, this)
    }

    override fun clickListener(movie: Movie) {
        findNavController().navigate(MovieTrendingFragmentDirections.actionTrendingFragmentToMovieDetailFragment(movie.id))
    }

}