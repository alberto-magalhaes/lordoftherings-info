package com.albertomagalhaes.lordoftheringsinfo.enum

import com.albertomagalhaes.lordoftheringsinfo.R

enum class MovieDataEnum(
    val movieID: String,
    val coverImg: Int,
    val movieTitle: String,
    val movieSubtitle: String,
    val releaseYear: Int
) {
    LOTR_SERIES(
        "5cd95395de30eff6ebccde56",
        R.drawable.lotr_series_cover,
        "The Lord of the Rings",
        "Series",
        2001
    ),
    LOTR_1(
        "5cd95395de30eff6ebccde5c",
        R.drawable.lotr_1_cover,
        "The Lord of the Rings",
        "The fellowship of the Ring",
        2001
    ),
    LOTR_2(
        "5cd95395de30eff6ebccde5b",
        R.drawable.lotr_2_cover,
        "The Lord of the Rings",
        "The Two Towers",
        2002
    ),
    LOTR_3(
        "5cd95395de30eff6ebccde5d",
        R.drawable.lotr_3_cover,
        "The Lord of the Rings",
        "The Return of the King",
        2003
    ),
    HOBBIT_SERIES(
        "5cd95395de30eff6ebccde57",
        R.drawable.hobbit_series_cover,
        "Hobbit",
        "Series",
        2012
    ),
    HOBBIT_1(
        "5cd95395de30eff6ebccde58",
        R.drawable.hobbit_1_cover,
        "Hobbit",
        "The Unexpected Journey",
        2012
    ),
    HOBBIT_2(
        "5cd95395de30eff6ebccde59",
        R.drawable.hobbit_2_cover,
        "Hobbit",
        "The Desolation of Smaug",
        2013
    ),
    HOBBIT_3(
        "5cd95395de30eff6ebccde5a",
        R.drawable.hobbit_3_cover,
        "Hobbit",
        "The Battle of the Five Armies",
        2014
    );

    companion object {
        fun getByID(id: String): MovieDataEnum? {
            return values().find {
                it.movieID == id
            }
        }
    }

}