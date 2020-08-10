package com.broprojects.studentcalendar.database.calendar

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Subjects::class, People::class, Tasks::class, Schedule::class, Tests::class], version = 1, exportSchema = false)
abstract class CalendarDatabase : RoomDatabase() {

    abstract val CalendarDao: CalendarDAO

    companion object {

        @Volatile
        private var INSTANCE: CalendarDatabase? = null

        fun getInstance(context: Context): CalendarDatabase? {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CalendarDatabase::class.java,
                        "calendar_database"
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