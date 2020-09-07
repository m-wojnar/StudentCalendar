package com.broprojects.studentcalendar.schedule

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.broprojects.studentcalendar.database.CoursesTableDao
import com.broprojects.studentcalendar.database.PeopleTableDao
import com.broprojects.studentcalendar.database.SchedulesTableDao

class ScheduleViewModelFactory(
    private val activity: Activity,
    private val dao: SchedulesTableDao,
    private val coursesDao: CoursesTableDao,
    private val peopleDao: PeopleTableDao,
    private val scheduleId: Long?
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(activity, dao, coursesDao, peopleDao, scheduleId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}