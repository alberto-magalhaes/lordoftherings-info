package com.albertomagalhaes.lordoftheringsinfo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel

@Dao
interface QuoteDAO {

    @Query("SELECT * FROM table_quote")
    suspend fun getAll(): List<QuoteModel>

    @Query("SELECT * FROM table_quote tq WHERE tq.movie = :movieID AND tq.character = :characterID")
    suspend fun getCharacterQuotesByMovie(
        movieID: String,
        characterID: String
    ): List<QuoteModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<QuoteModel>)

}