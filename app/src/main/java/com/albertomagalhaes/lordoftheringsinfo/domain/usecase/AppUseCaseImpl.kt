package com.albertomagalhaes.lordoftheringsinfo.domain.usecase

import com.albertomagalhaes.lordoftheringsinfo.enum.MovieDataEnum
import com.albertomagalhaes.lordoftheringsinfo.data.repository.AppRepositoryImpl
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel

class AppUseCaseImpl(
    val repository: AppRepositoryImpl
): AppUseCase {

    override suspend fun updateMovies(
        onSuccess: (List<MovieModel>?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        repository.getOrSyncMovies(
            onSuccess = {
                val moviesOrdered = it?.sortedBy { movie ->
                    MovieDataEnum.getByID(movie.id)?.releaseYear
                }
                onSuccess.invoke(moviesOrdered)
            },
            onError
        )
    }

    override suspend fun updateCharacters(
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        repository.getOrSyncCharacters(
            onSuccess,
            onError
        )
    }

    override suspend fun updateQuotes(
        onSuccess: (List<QuoteModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        repository.getOrSyncQuotes(
            onSuccess,
            onError
        )
    }

    override suspend fun getCharacterQuotesByMovie(
        movieID: String,
        characterID: String,
        onSuccess: (List<QuoteModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        repository.getCharacterQuotesByMovie(
            movieID,
            characterID,
            onSuccess,
            onError
        )
    }

    override suspend fun getCharacterByMovie(
        movieID: String,
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        repository.getCharacterByMovie(
            movieID,
            onSuccess,
            onError
        )
    }

    suspend fun favoriteMovie(movie: MovieModel){
        repository.favoriteMovie(movie)
    }
}