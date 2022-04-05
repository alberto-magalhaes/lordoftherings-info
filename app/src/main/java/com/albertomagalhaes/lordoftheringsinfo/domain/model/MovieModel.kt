package com.albertomagalhaes.lordoftheringsinfo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

@Entity(tableName = "table_movie")
data class MovieModel(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    val name: String?,
    val runtimeInMinutes: Int?,
    val budgetInMillions: Int?,
    val boxOfficeRevenueInMillions: Double?,
    val academyAwardNominations: Int?,
    val academyAwardWins: Int?,
    val rottenTomatoesScore: Double?
){
    val rottenTomatoesScoreFormatted: Int?
        get() = rottenTomatoesScore?.roundToInt()

    var isFavorite: Boolean = false
}