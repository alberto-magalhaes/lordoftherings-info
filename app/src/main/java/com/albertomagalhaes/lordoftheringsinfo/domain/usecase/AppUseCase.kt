package com.albertomagalhaes.lordoftheringsinfo.domain.usecase

import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel

interface AppUseCase {
    suspend fun updateMovies(
        onSuccess: (List<MovieModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateCharacters(
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun updateQuotes(
        onSuccess: (List<QuoteModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun getCharacterQuotesByMovie(
        movieID: String,
        characterID: String,
        onSuccess: (List<QuoteModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun getCharacterByMovie(
        movieID: String,
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

}