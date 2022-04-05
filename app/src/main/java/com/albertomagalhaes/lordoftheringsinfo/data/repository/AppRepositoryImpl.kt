package com.albertomagalhaes.lordoftheringsinfo.data.repository

import com.albertomagalhaes.lordoftheringsinfo.data.local.CharacterDAO
import com.albertomagalhaes.lordoftheringsinfo.data.local.MovieDAO
import com.albertomagalhaes.lordoftheringsinfo.data.local.QuoteDAO
import com.albertomagalhaes.lordoftheringsinfo.data.remote.api.TheOneAPI
import com.albertomagalhaes.lordoftheringsinfo.data.remote.service.RetrofitService.getService
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel
import com.albertomagalhaes.lordoftheringsinfo.domain.repository.AppRepository

class AppRepositoryImpl(
    private val movieDAO: MovieDAO,
    private val quoteDAO: QuoteDAO,
    private val characterDAO: CharacterDAO
): AppRepository {

    private val service = getService<TheOneAPI>()

    override suspend fun getOrSyncMovies(
        onSuccess: (List<MovieModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        getOrSync(
            local = { movieDAO.getAll() },
            remote = { service.getMovieList() },
            onSuccess = onSuccess,
            onError = onError,
            onPersist = { movieDAO.insertAll(it) }
        )
    }

    override suspend fun getOrSyncCharacters(
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        getOrSync(
            local = { characterDAO.getAll() },
            remote = { service.getCharacterList() },
            onSuccess = onSuccess,
            onError = onError,
            onPersist = { characterDAO.insertAll(it) }
        )
    }

    override suspend fun getOrSyncQuotes(
        onSuccess: (List<QuoteModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        getOrSync(
            local = { quoteDAO.getAll() },
            remote = { service.getQuoteList() },
            onSuccess = onSuccess,
            onError = onError,
            onPersist = { quoteDAO.insertAll(it) }
        )
    }

    override suspend fun getCharacterQuotesByMovie(
        movieID: String,
        characterID: String,
        onSuccess: (List<QuoteModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        get(
            local = { quoteDAO.getCharacterQuotesByMovie(movieID, characterID) },
            onSuccess = { onSuccess.invoke(it) },
            onError = { onError.invoke(it) }
        )
    }

    override suspend fun getCharacterByMovie(
        movieID: String,
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        get(
            local = { characterDAO.getCharactersByMovie(movieID) },
            onSuccess = { onSuccess.invoke(it) },
            onError = { onError.invoke(it) }
        )
    }

    override suspend fun favoriteMovie(movie: MovieModel) {
        try {
            movieDAO.update(movie)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

}