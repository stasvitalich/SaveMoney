package com.example.savemoney.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.savemoney.data.dao.GroceryDao

import com.example.savemoney.data.entities.GroceryEntity

// Defines the database with the entities and the version, exportSchema set to false.
@Database(entities = [GroceryEntity::class], version = 2, exportSchema = false)
abstract class GroceryDatabase : RoomDatabase() {

    // Provides access to the GroceryDao for database operations.
    abstract fun groceryDao(): GroceryDao

    companion object {
        // Singleton instance of GroceryDatabase, volatile to ensure atomic access to the variable.
        @Volatile
        private var INSTANCE: GroceryDatabase? = null

        // Returns the singleton instance of the database or creates a new one if none exists.
        fun getDatabase(context: Context): GroceryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}