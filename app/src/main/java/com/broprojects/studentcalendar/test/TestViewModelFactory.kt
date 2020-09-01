package com.broprojects.studentcalendar.test

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.broprojects.studentcalendar.database.TestsTableDao

class TestViewModelFactory(private val activity: Activity, private val dao: TestsTableDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel(activity, dao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}