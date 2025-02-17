package com.mwojnar.studentcalendar.course

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mwojnar.studentcalendar.database.CoursesTableDao

class CourseViewModelFactory(
    private val activity: Activity,
    private val dao: CoursesTableDao,
    private val courseId: Long?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseViewModel(activity, dao, courseId) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}