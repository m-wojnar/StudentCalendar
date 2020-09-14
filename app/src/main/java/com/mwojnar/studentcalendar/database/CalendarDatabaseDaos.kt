package com.mwojnar.studentcalendar.database

import androidx.room.*
import java.util.*

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(obj: T): Long

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)

    fun get(id: Long): T?
}

@Dao
interface TasksTableDao : BaseDao<Task> {
    @Query("SELECT * FROM tasks WHERE taskId = :id")
    override fun get(id: Long): Task?

    @Transaction
    @Query("SELECT * FROM tasks ORDER BY priority DESC, whenDateTime")
    fun getAllWithCourse(): List<TaskAndCourse>?

    @Transaction
    @Query("SELECT * FROM tasks WHERE whenDateTime >= :startDate AND whenDateTime <= :endDate")
    fun getYourDayItems(startDate: Date, endDate: Date): List<TaskAndCourse>
}

@Dao
interface TestsTableDao : BaseDao<Test> {
    @Query("SELECT * FROM tests WHERE testId = :id")
    override fun get(id: Long): Test?

    @Transaction
    @Query("SELECT * FROM tests ORDER BY whenDateTime")
    fun getAllWithCourse(): List<TestAndCourse>?

    @Transaction
    @Query("SELECT * FROM tests WHERE whenDateTime >= :startDate AND whenDateTime <= :endDate")
    fun getYourDayItems(startDate: Date, endDate: Date): List<TestAndCourse>
}

@Dao
interface SchedulesTableDao : BaseDao<Schedule> {
    @Query("SELECT * FROM schedules WHERE scheduleId = :id")
    override fun get(id: Long): Schedule?

    @Transaction
    @Query("SELECT * FROM schedules ORDER BY courseId")
    fun getAllWithCourseAndPerson(): List<ScheduleAndCourseAndPerson>?

    @Transaction
    @Query("SELECT * FROM schedules WHERE startDate <= :date AND endDate >= :date AND weekday == :weekday")
    fun getYourDayItems(date: Date, weekday: Int): List<ScheduleAndCourseAndPerson>
}

@Dao
interface CoursesTableDao : BaseDao<Course> {
    @Query("SELECT * FROM courses WHERE courseId = :id")
    override fun get(id: Long): Course?

    @Query("SELECT * FROM courses ORDER BY courseId")
    fun getAll(): List<Course>?
}

@Dao
interface PeopleTableDao : BaseDao<Person> {
    @Query("SELECT * FROM people WHERE personId = :id")
    override fun get(id: Long): Person?

    @Query("SELECT * FROM people ORDER BY lastName, firstName")
    fun getAll(): List<Person>?
}