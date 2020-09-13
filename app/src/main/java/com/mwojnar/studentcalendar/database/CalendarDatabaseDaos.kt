package com.mwojnar.studentcalendar.database

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(obj: T): Long

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)

    fun get(id: Long): T?
    fun getAll(): List<T>?
}

@Dao
interface TasksTableDao : BaseDao<Task> {
    @Query("SELECT * FROM tasks WHERE taskId = :id")
    override fun get(id: Long): Task?

    @Query("SELECT * FROM tasks ORDER BY priority DESC, whenDateTime")
    override fun getAll(): List<Task>?
}

@Dao
interface TestsTableDao : BaseDao<Test> {
    @Query("SELECT * FROM tests WHERE testId = :id")
    override fun get(id: Long): Test?

    @Query("SELECT * FROM tests ORDER BY whenDateTime")
    override fun getAll(): List<Test>?
}

@Dao
interface SchedulesTableDao : BaseDao<Schedule> {
    @Query("SELECT * FROM schedules WHERE scheduleId = :id")
    override fun get(id: Long): Schedule?

    @Query("SELECT * FROM schedules ORDER BY courseId")
    override fun getAll(): List<Schedule>?
}

@Dao
interface CoursesTableDao : BaseDao<Course> {
    @Query("SELECT * FROM courses WHERE courseId = :id")
    override fun get(id: Long): Course?

    @Query("SELECT * FROM courses ORDER BY courseId")
    override fun getAll(): List<Course>?

    @Query("SELECT name, courseId FROM courses ORDER BY courseId")
    fun getDropdownList(): List<CoursesDropdownItem>?
}

@Dao
interface PeopleTableDao : BaseDao<Person> {
    @Query("SELECT * FROM people WHERE personId = :id")
    override fun get(id: Long): Person?

    @Query("SELECT * FROM people ORDER BY lastName, firstName")
    override fun getAll(): List<Person>?

    @Query("SELECT lastName, firstName, personId FROM people ORDER BY lastName, firstName")
    fun getDropdownList(): List<PeopleDropdownItem>?
}