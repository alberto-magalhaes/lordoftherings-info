package com.albertomagalhaes.lordoftheringsinfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.albertomagalhaes.lordoftheringsinfo.R
import com.albertomagalhaes.lordoftheringsinfo.databinding.FragmentMovieListBinding
import com.albertomagalhaes.lordoftheringsinfo.databinding.ItemMovieBinding
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.enum.MovieDataEnum
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieListFragment : Fragment() {

    lateinit var binding: FragmentMovieListBinding
    private val viewModel by sharedViewModel<AppViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.updateMovies()
    }

    private fun setupObservers(){
        viewModel.movieList.observe(viewLifecycleOwner){
            setupMovieList(it)
        }
    }

    private fun setupMovieList(movies: List<MovieModel>){
        binding.rvMovieList.adapter = SingleTypeGenericAdapter(
            ItemMovieBinding::inflate,
            movies
        ){ item, binding, _, _ ->
            binding.setup(item)
        }
    }

    private fun ItemMovieBinding.setup(item: MovieModel){
        MovieDataEnum.getByID(item.id)?.let {
            tvMovieTitle.text = it.movieTitle
            tvMovieSubtitle.text = it.movieSubtitle
            tvMovieRelease.text = it.releaseYear.toString()
            ivMovieCover.setImageDrawable(ContextCompat.getDrawable(requireContext(), it.coverImg))
        }

        tvAwardNomination.text = resources.getString(R.string.n_award_nominations, item.academyAwardNominations)
        tvAwardWon.text = resources.getString(R.string.n_awards_won, item.academyAwardWins)
        tvRottenScore.text = item.rottenTomatoesScoreFormatted.toString()
        viewScoreIndicator.setScoreIndicatorColor(item.rottenTomatoesScoreFormatted)
        root.setOnClickListener {
            viewModel.redirectToDetail(item)
        }
        btnFavorite.apply {
            setFavoriteColor(item.isFavorite)
            setOnClickListener {
                viewModel.favoriteMovie(item)
                setFavoriteColor(item.isFavorite)
            }
        }
        btnFavorite.setFavoriteColor(item.isFavorite)
        btnFavorite.setOnClickListener {
            viewModel.favoriteMovie(item)
            btnFavorite.setFavoriteColor(item.isFavorite)
            showSnackMessage(item)
        }
    }

    private fun ItemMovieBinding.showSnackMessage(movie: MovieModel){
        val text =
                if(movie.isFavorite) resources.getString(R.string.movie_favorites_added, movie.name)
                else resources.getString(R.string.movie_favorites_removed, movie.name)
        Snackbar.make(root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun AppCompatImageButton.setFavoriteColor(isFavourite: Boolean){
        val colorID =
            if(isFavourite) R.color.accent
            else R.color.gray_op_100

        DrawableCompat.setTint(
            DrawableCompat.wrap(this.background),
            ContextCompat.getColor(context, colorID)
        )
    }

    private fun View.setScoreIndicatorColor(score: Int?){
        when(score){
            in 0..50 -> setBackgroundColor(ContextCompat.getColor(context, R.color.red_op_100))
            in 70..100 -> setBackgroundColor(ContextCompat.getColor(context, R.color.green_op_100))
            null -> visibility = View.GONE
            else -> setBackgroundColor(ContextCompat.getColor(context, R.color.yellow_op_100))
        }
    }

}