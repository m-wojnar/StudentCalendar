package com.broprojects.studentcalendar.database.calendar

import androidx.room.*

@Dao
interface CalendarDAO {

    //Tutaj do każdego: Tasks, People, Schedule itd trzeba dać Insert, Update, Delete?

    @Transaction
    @Query("SELECT * FROM Subjects_table")
    fun getSubjectWithPeople(): List<SubjectWithPeople>

    @Transaction
    @Query("SELECT * FROM People_table")
    fun getPeopleWithSubjects(): List<PeopleWithSubjects>

    @Transaction
    @Query("SELECT * FROM Subjects_table")
    fun getSubjectWithTests(): List<SubjectWithTests>

    @Transaction
    @Query("SELECT * FROM Subjects_table")
    fun getSubjectWithTasks(): List<SubjectWithTasks>

    @Transaction
    @Query("SELECT * FROM Subjects_table")
    fun getSubjectWithSchedule(): List<SubjectWithSchedule>
}