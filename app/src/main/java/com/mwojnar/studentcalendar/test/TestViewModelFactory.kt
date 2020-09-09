package com.mwojnar.studentcalendar.test

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwojnar.studentcalendar.database.CoursesTableDao
import com.mwojnar.studentcalendar.database.TestsTableDao

class TestViewModelFactory(
    private val activity: Activity,
    private val dao: TestsTableDao,
    private val coursesDao: CoursesTableDao,
    private val testId: Long?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TestViewModel(activity, dao, coursesDao, testId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}