package com.albertomagalhaes.lordoftheringsinfo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "table_quote")
data class QuoteModel(
    @PrimaryKey
    @SerializedName("_id")
    val id: String,
    val dialog: String?,
    val movie: String?,
    val character: String?
)