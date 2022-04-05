package com.albertomagalhaes.lordoftheringsinfo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_character")
data class CharacterModel(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    val height: String?,
    val race: String?,
    val gender: String?,
    val birth: String?,
    val spouse: String?,
    val death: String?,
    val realm: String?,
    val hair: String?,
    val name: String?,
    val wikiUrl: String?,
    var showQuotes: Boolean = false
)