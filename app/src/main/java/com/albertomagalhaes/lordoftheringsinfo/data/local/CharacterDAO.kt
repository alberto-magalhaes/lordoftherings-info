package com.albertomagalhaes.lordoftheringsinfo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel

@Dao
interface CharacterDAO {

    @Query("SELECT * FROM table_character")
    suspend fun getAll(): List<CharacterModel>

    @Query("SELECT * FROM table_character tc WHERE tc.id IN (SELECT DISTINCT character FROM table_quote WHERE movie = :movieID)")
    suspend fun getCharactersByMovie(movieID: String): List<CharacterModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterModel>)

}