package com.albertomagalhaes.lordoftheringsinfo.data.remote.api

import com.albertomagalhaes.lordoftheringsinfo.data.remote.dto.ResponseDTO
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface TheOneAPI {

    @GET("movie/")
    suspend fun getMovieList() : Response<ResponseDTO<MovieModel>>

    @GET("character/")
    suspend fun getCharacterList() : Response<ResponseDTO<CharacterModel>>

    @GET("quote/")
    suspend fun getQuoteList() : Response<ResponseDTO<QuoteModel>>

}