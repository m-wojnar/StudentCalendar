package com.broprojects.studentcalendar.schedule

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.broprojects.studentcalendar.database.*
import com.broprojects.studentcalendar.helpers.InputViewModel
import java.util.*

class ScheduleViewModel(
    activity: Activity,
    dao: SchedulesTableDao,
    private val coursesDao: CoursesTableDao,
    private val peopleDao: PeopleTableDao,
    scheduleId: Long?
) : InputViewModel<Schedule>(activity, dao, scheduleId, Schedule()) {

    private val coursesMutableLiveData = MutableLiveData<List<CoursesDropdownItem>>()
    val coursesList: LiveData<List<CoursesDropdownItem>>
        get() = coursesMutableLiveData

    private val selectedCourseMutableLiveData = MutableLiveData<Course>()
    val selectedCourse: LiveData<Course>
        get() = selectedCourseMutableLiveData

    private val peopleMutableLiveData = MutableLiveData<List<PeopleDropdownItem>>()
    val peopleList: LiveData<List<PeopleDropdownItem>>
        get() = peopleMutableLiveData

    private val selectedPersonMutableLiveData = MutableLiveData<Person>()
    val selectedPerson: LiveData<Person>
        get() = selectedPersonMutableLiveData

    init {
        dbOperation { coursesMutableLiveData.postValue(coursesDao.getDropdownList()) }
        dbOperation { peopleMutableLiveData.postValue(peopleDao.getDropdownList()) }
    }

    fun loadCourseAndPersonName() {
        if (model.value?.courseId != null ){
            dbOperation { selectedCourseMutableLiveData.postValue(coursesDao.get(model.value?.courseId!!)) }
        }

        if (model.value?.personId != null) {
            dbOperation { selectedPersonMutableLiveData.postValue(peopleDao.get(model.value?.personId!!)) }
        }
    }

    fun setWhenTime(whenTime: Date) {
        modelMutableLiveData.value?.whenTime = whenTime
    }

    fun setStartDate(startDate: Date) {
        modelMutableLiveData.value?.startDate = startDate
    }

    fun setEndDate(endDate: Date) {
        modelMutableLiveData.value?.endDate = endDate
    }

    fun setCourse(courseId: Long) {
        modelMutableLiveData.value?.courseId = courseId
    }

    fun setPerson(personId: Long) {
        modelMutableLiveData.value?.personId = personId
    }
}