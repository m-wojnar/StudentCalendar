package com.mwojnar.studentcalendar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mwojnar.studentcalendar.helpers.Converters

@Database(
    entities = [Task::class, Test::class, Schedule::class, Course::class, Person::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CalendarDatabase : RoomDatabase() {

    abstract val tasksTableDao: TasksTableDao
    abstract val testsTableDao: TestsTableDao
    abstract val schedulesTableDao: SchedulesTableDao
    abstract val coursesTableDao: CoursesTableDao
    abstract val peopleTableDao: PeopleTableDao

    companion object {

        @Volatile
        private var INSTANCE: CalendarDatabase? = null

        fun getInstance(context: Context): CalendarDatabase {
            synchronized(this) {
                INSTANCE = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "calendar_database"
                ).build()

                return INSTANCE as CalendarDatabase
            }
        }
    }
}