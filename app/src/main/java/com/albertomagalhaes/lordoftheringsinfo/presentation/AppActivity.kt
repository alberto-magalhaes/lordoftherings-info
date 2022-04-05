package com.albertomagalhaes.lordoftheringsinfo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.albertomagalhaes.lordoftheringsinfo.R
import com.albertomagalhaes.lordoftheringsinfo.databinding.ActivityAppBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        getOrSyncOnce()
    }

    private fun getOrSyncOnce(){
        val viewModel: AppViewModel by viewModel{
            parametersOf(navController)
        }
        viewModel.apply {
            updateQuotes()
            updateMovies()
            updateCharacters()
        }
    }

}