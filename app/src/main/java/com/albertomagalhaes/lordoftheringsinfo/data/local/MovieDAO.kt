package com.albertomagalhaes.lordoftheringsinfo.data.local

import androidx.room.*
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel

@Dao
interface MovieDAO {

    @Query("SELECT * FROM table_movie")
    suspend fun getAll(): List<MovieModel>

    @Query("SELECT * FROM table_movie WHERE id = :id")
    suspend fun get(id: String): MovieModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieModel)

    @Update
    suspend fun update(movie: MovieModel)

}