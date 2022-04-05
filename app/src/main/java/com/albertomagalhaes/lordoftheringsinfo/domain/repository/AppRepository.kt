package com.albertomagalhaes.lordoftheringsinfo.domain.repository

import com.albertomagalhaes.lordoftheringsinfo.data.remote.dto.ResponseDTO
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel
import retrofit2.Response

interface AppRepository {

    suspend fun getOrSyncMovies(
        onSuccess: (List<MovieModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun getOrSyncCharacters(
        onSuccess: (List<CharacterModel>?) -> Unit,
        onError: (Exception) -> Unit
    )

    suspend fun getOrSyncQuotes(
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

    suspend fun <T> getOrSync(
        local: suspend () -> List<T>,
        remote: suspend () -> Response<ResponseDTO<T>>,
        onSuccess: (List<T>?) -> Unit,
        onError: (Exception) -> Unit,
        onPersist: (suspend (List<T>) -> Unit)? = null
    ){
        try {
            val localData = local.invoke()
            if(localData.isEmpty()){
                val remoteData = remote.invoke().body()?.docs
                if(!remoteData.isNullOrEmpty()){
                    onSuccess.invoke(remoteData)
                    onPersist?.invoke(remoteData)
                }
            } else {
                onSuccess.invoke(localData)
            }
        } catch (e: Exception){
            onError.invoke(e)
        }
    }

    suspend fun <T> get(
        local: suspend () -> List<T>,
        onSuccess: (List<T>?) -> Unit,
        onError: (Exception) -> Unit
    ){
        try {
            val localData = local.invoke()
            onSuccess.invoke(localData)
        } catch (e: Exception){
            onError.invoke(e)
        }
    }

    suspend fun favoriteMovie(movie: MovieModel)
}