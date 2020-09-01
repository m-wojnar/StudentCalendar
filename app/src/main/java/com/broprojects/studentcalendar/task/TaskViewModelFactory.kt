package com.broprojects.studentcalendar.task

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.broprojects.studentcalendar.database.TasksTableDao

class TaskViewModelFactory(private val activity: Activity, private val dao: TasksTableDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TaskViewModel(activity, dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}