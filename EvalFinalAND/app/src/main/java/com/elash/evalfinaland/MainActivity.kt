package com.elash.evalfinaland

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.elash.evalfinaland.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var navController : NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.movieSearchFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.trendingFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.movieDetailFragment -> binding.bottomNavigationView.visibility = View.GONE
            }
        }


        val bottomNavigationView = binding.bottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }


}