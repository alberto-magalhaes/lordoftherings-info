package com.albertomagalhaes.lordoftheringsinfo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.albertomagalhaes.lordoftheringsinfo.domain.model.CharacterModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.MovieModel
import com.albertomagalhaes.lordoftheringsinfo.domain.model.QuoteModel

@Database(
    entities = [
        MovieModel::class,
        QuoteModel::class,
        CharacterModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO
    abstract val quoteDAO: QuoteDAO
    abstract val characterDAO: CharacterDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}