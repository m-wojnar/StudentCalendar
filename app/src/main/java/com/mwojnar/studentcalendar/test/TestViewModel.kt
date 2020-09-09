package com.mwojnar.studentcalendar.test

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mwojnar.studentcalendar.database.*
import com.mwojnar.studentcalendar.helpers.InputViewModel
import java.util.*

class TestViewModel(
    activity: Activity,
    dao: TestsTableDao,
    private val coursesDao: CoursesTableDao,
    testId: Long?
) : InputViewModel<Test>(activity, dao, testId, Test()) {

    private val coursesMutableLiveData = MutableLiveData<List<CoursesDropdownItem>>()
    val coursesList: LiveData<List<CoursesDropdownItem>>
        get() = coursesMutableLiveData

    private val selectedCourseMutableLiveData = MutableLiveData<Course>()
    val selectedCourse: LiveData<Course>
        get() = selectedCourseMutableLiveData

    init {
        dbOperation { coursesMutableLiveData.postValue(coursesDao.getDropdownList()) }
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