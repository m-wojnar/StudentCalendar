package com.broprojects.studentcalendar.schedule

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.broprojects.studentcalendar.database.Schedule
import com.broprojects.studentcalendar.database.SchedulesTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel
import com.broprojects.studentcalendar.helpers.toDateString
import com.broprojects.studentcalendar.helpers.toTimeString
import java.util.*

class ScheduleViewModel(activity: Activity, dao: SchedulesTableDao, private val scheduleId: Long?) :
    InputViewModel<Schedule>(activity, dao) {

    private val _schedule = MutableLiveData(Schedule())
    val schedule: LiveData<Schedule>
        get() = _schedule

    val whenTime = Transformations.map(schedule) {
        schedule.value?.whenTime?.toTimeString(activity.applicationContext)
    }

    val startDate = Transformations.map(schedule) {
        schedule.value?.startDate?.toDateString(activity.applicationContext)
    }

    val endDate = Transformations.map(schedule) {
        schedule.value?.endDate?.toDateString(activity.applicationContext)
    }

    init {
        if (scheduleId != null) {
            _schedule.value = getData(scheduleId)
        }
    }

    fun setWhenTime(whenTime: Date) {
        _schedule.value?.whenTime = whenTime
    }

    fun setStartDate(startDate: Date) {
        _schedule.value?.startDate = startDate
    }

    fun setEndDate(endDate: Date) {
        _schedule.value?.endDate = endDate
    }

    fun saveData() {
        super.saveData(scheduleId, _schedule.value!!)
    }
}