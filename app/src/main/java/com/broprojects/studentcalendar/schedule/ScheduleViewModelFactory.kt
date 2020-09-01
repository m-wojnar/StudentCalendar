package com.broprojects.studentcalendar.schedule

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.broprojects.studentcalendar.database.SchedulesTableDao

class ScheduleViewModelFactory(private val activity: Activity, private val dao: SchedulesTableDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(activity, dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}