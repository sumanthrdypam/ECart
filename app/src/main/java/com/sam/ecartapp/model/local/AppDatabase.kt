package com.sam.ecartapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sam.ecartapp.model.Product

@Database(entities = [Cart::class], version = 1, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){
    abstract fun cartDao() : CartDao

    companion object {
        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            if (!this::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ECart"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }

}