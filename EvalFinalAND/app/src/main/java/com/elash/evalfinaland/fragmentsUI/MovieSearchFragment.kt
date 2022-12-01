package com.elash.evalfinaland.fragmentsUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elash.evalfinaland.adapters.SearchMovieRowAdapter
import com.elash.evalfinaland.databinding.FragmentMovieSearchBinding
import com.elash.evalfinaland.model.Movie
import com.elash.evalfinaland.services.MovieServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieSearchFragment : Fragment(), SearchMovieRowAdapter.ClickListener {

    private var binding: FragmentMovieSearchBinding? = null
    private val movieService by lazy { MovieServiceImpl() }
    private var title: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieSearchBinding.inflate(layoutInflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchTextField?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                title = binding?.searchTextField?.query.toString()
                searchMovies(title)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                title = binding?.searchTextField?.query.toString()
                searchMovies(title)
                return true
            }
        })
    }

    fun searchMovies(query: String?) {

        if(query.isNullOrEmpty()) {
            setUpRecyclerView(listOf())
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val response = movieService.searchMovies(query)
                withContext(Dispatchers.Main){
                    if(response.isSuccessful){
                        response.body()?.results?.let {
                            setUpRecyclerView(it)
                        }
                    }
                }
            }
        }

    }

    private fun setUpRecyclerView(data: List<Movie>) {
        val recyclerView = binding?.SearchMovieRecyclerView
        recyclerView?.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = SearchMovieRowAdapter(data, this)


    }

    override fun clickListener(movie: Movie) {
        findNavController().navigate(MovieSearchFragmentDirections.actionMovieSearchFragmentToMovieDetailFragment(movie.id))
    }


}