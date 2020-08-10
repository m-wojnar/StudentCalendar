package com.broprojects.studentcalendar.database.settings

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Settings::class], version = 1, exportSchema = false)
abstract class SettingsDatabase : RoomDatabase() {

    abstract val SettingsDao: SettingsDAO

    companion object {

        @Volatile
        private var INSTANCE: SettingsDatabase? = null

        fun getInstance(context: Context): SettingsDatabase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SettingsDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}