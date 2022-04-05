package com.albertomagalhaes.lordoftheringsinfo.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.albertomagalhaes.lordoftheringsinfo.R
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel
import com.albertomagalhaes.lordoftheringsinfo.domain.usecase.AppUseCaseImpl
import kotlinx.coroutines.launch

class AppViewModel(
    val appUseCase: AppUseCaseImpl,
    val navController: NavController
): ViewModel() {

    private val _movieList = MutableLiveData<List<MovieModel>>()
    val movieList: LiveData<List<MovieModel>> = _movieList

    private val characterList = MutableLiveData<List<CharacterModel>>()
    private val quoteList = MutableLiveData<List<QuoteModel>>()
    private val characterQuotesByMovie = MutableLiveData<List<QuoteModel>>()

    lateinit var movieSelected: MovieModel
    lateinit var characterListByMovie: List<CharacterModel>

    fun updateMovies(){
        viewModelScope.launch {
            appUseCase.updateMovies(
                onSuccess = {
                    _movieList.postValue(it)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun updateQuotes(){
        viewModelScope.launch {
            appUseCase.updateQuotes(
                onSuccess = {
                    quoteList.postValue(it)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    fun updateCharacters(){
        viewModelScope.launch {
            appUseCase.updateCharacters(
                onSuccess = {
                    characterList.postValue(it)
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }

    private fun getCharactersByMovie(
        movieID: String,
        finally: ((List<CharacterModel>?) -> Unit)? = null
    ){
        viewModelScope.launch {
            appUseCase.getCharacterByMovie(
                movieID,
                onSuccess = {
                    characterListByMovie = it.orEmpty()
                    finally?.invoke(it.orEmpty())
                },
                onError = {
                    it.printStackTrace()
                    finally?.invoke(null)
                }
            )
        }
    }

    fun getCharacterQuotesByMovie(
        movieID: String,
        characterID: String,
        finally: ((List<QuoteModel>?) -> Unit)? = null
    ){
        viewModelScope.launch {
            appUseCase.getCharacterQuotesByMovie(
                movieID,
                characterID,
                onSuccess = {
                    characterQuotesByMovie.postValue(it)
                    finally?.invoke(it)
                },
                onError = {
                    it.printStackTrace()
                    finally?.invoke(null)
                }
            )
        }
    }

    fun favoriteMovie(movie: MovieModel){
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            appUseCase.favoriteMovie(movie)
        }
    }

    fun redirectToDetail(movie: MovieModel){
        movieSelected = movie
        getCharactersByMovie(movie.id){
            navController.navigate(R.id.redirect_movie_list_to_movie_detail)
        }
    }

    fun redirectToMovieList(){
        navController.navigate(R.id.redirect_movie_detail_to_movie_list)
    }

}