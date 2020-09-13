package com.mwojnar.studentcalendar.schedule

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mwojnar.studentcalendar.R
import com.mwojnar.studentcalendar.database.*
import com.mwojnar.studentcalendar.helpers.InputViewModel
import com.mwojnar.studentcalendar.helpers.ValueDropdownItem
import java.util.*

class ScheduleViewModel(
    activity: Activity,
    dao: SchedulesTableDao,
    private val coursesDao: CoursesTableDao,
    private val peopleDao: PeopleTableDao,
    scheduleId: Long?
) : InputViewModel<Schedule>(activity, dao, scheduleId, Schedule()) {

    val weekdayArray = arrayOf(
        ValueDropdownItem(getString(R.string.monday), 1),
        ValueDropdownItem(getString(R.string.tuesday), 2),
        ValueDropdownItem(getString(R.string.wednesday), 3),
        ValueDropdownItem(getString(R.string.thursday), 4),
        ValueDropdownItem(getString(R.string.friday), 5),
        ValueDropdownItem(getString(R.string.saturday), 6),
        ValueDropdownItem(getString(R.string.sunday), 7)
    )

    val weekdayTextMap = mapOf(
        Pair(1, getString(R.string.monday)),
        Pair(2, getString(R.string.tuesday)),
        Pair(3, getString(R.string.wednesday)),
        Pair(4, getString(R.string.thursday)),
        Pair(5, getString(R.string.friday)),
        Pair(6, getString(R.string.saturday)),
        Pair(7, getString(R.string.sunday))
    )

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

    fun setWeekday(weekday: Int) {
        modelMutableLiveData.value?.weekday = weekday
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