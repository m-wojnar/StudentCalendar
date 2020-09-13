package com.mwojnar.studentcalendar.test

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mwojnar.studentcalendar.database.Course
import com.mwojnar.studentcalendar.database.CoursesTableDao
import com.mwojnar.studentcalendar.database.Test
import com.mwojnar.studentcalendar.database.TestsTableDao
import com.mwojnar.studentcalendar.helpers.InputViewModel
import java.util.*

class TestViewModel(
    activity: Activity,
    dao: TestsTableDao,
    private val coursesDao: CoursesTableDao,
    testId: Long?
) : InputViewModel<Test>(activity, dao, testId, Test()) {

    private val coursesMutableLiveData = MutableLiveData<List<Course>>()
    val coursesList: LiveData<List<Course>>
        get() = coursesMutableLiveData

    private val selectedCourseMutableLiveData = MutableLiveData<Course>()
    val selectedCourse: LiveData<Course>
        get() = selectedCourseMutableLiveData

    init {
        dbOperation { coursesMutableLiveData.postValue(coursesDao.getAll()) }
    }

    fun loadCourseName() {
        if (model.value?.courseId != null ){
            dbOperation { selectedCourseMutableLiveData.postValue(coursesDao.get(model.value?.courseId!!)) }
        }
    }

    fun setWhenDateTime(whenDateTime: Date) {
        modelMutableLiveData.value?.whenDateTime = whenDateTime
    }

    fun setCourse(courseId: Long) {
        modelMutableLiveData.value?.courseId = courseId
    }
}