package com.broprojects.studentcalendar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(obj: T)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)

    fun get(id: Long): T?
    fun getAll(): LiveData<List<T>>?
}

@Dao
interface TasksTableDao: BaseDao<Task> {
    @Query("SELECT * FROM tasks WHERE taskId = :id")
    override fun get(id: Long): Task?

    @Query("SELECT * FROM tasks ORDER BY priority DESC, whenDateTime")
    override fun getAll(): LiveData<List<Task>>?
}

@Dao
interface TestsTableDao: BaseDao<Test> {
    @Query("SELECT * FROM tests WHERE testId = :id")
    override fun get(id: Long): Test?

    @Query("SELECT * FROM tests ORDER BY whenDateTime")
    override fun getAll(): LiveData<List<Test>>?
}

@Dao
interface SchedulesTableDao: BaseDao<Schedule> {
    @Query("SELECT * FROM schedules WHERE scheduleId = :id")
    override fun get(id: Long): Schedule?

    @Query("SELECT * FROM schedules ORDER BY courseId")
    override fun getAll(): LiveData<List<Schedule>>?
}

@Dao
interface CoursesTableDao: BaseDao<Course> {
    @Query("SELECT * FROM courses WHERE courseId = :id")
    override fun get(id: Long): Course?

    @Query("SELECT * FROM courses ORDER BY courseId")
    override fun getAll(): LiveData<List<Course>>?
}

@Dao
interface PeopleTableDao: BaseDao<Person> {
    @Query("SELECT * FROM people WHERE personId = :id")
    override fun get(id: Long): Person?

    @Query("SELECT * FROM people ORDER BY lastName, firstName")
    override fun getAll(): LiveData<List<Person>>?
}