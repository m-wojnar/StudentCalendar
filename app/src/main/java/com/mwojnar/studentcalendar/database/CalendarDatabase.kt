package com.mwojnar.studentcalendar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mwojnar.studentcalendar.helpers.Converters

@Database(
    entities = [Task::class, Test::class, Schedule::class, Course::class, Person::class],
    version = 2
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
                ).addMigrations(migration_1_2).build()

                return INSTANCE as CalendarDatabase
            }
        }

        private val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE courses_new (courseId INTEGER PRIMARY KEY, name TEXT NOT NULL, iconName TEXT, colorName TEXT)")
                database.execSQL("INSERT INTO courses_new (courseId, name) SELECT courseId, name FROM courses")
                database.execSQL("DROP TABLE courses")
                database.execSQL("ALTER TABLE courses_new RENAME TO courses")
            }
        }
    }
}