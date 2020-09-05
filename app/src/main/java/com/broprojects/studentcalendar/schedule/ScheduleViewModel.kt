package com.broprojects.studentcalendar.schedule

import android.app.Activity
import androidx.lifecycle.Transformations
import com.broprojects.studentcalendar.database.Schedule
import com.broprojects.studentcalendar.database.SchedulesTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel
import com.broprojects.studentcalendar.helpers.toDateString
import com.broprojects.studentcalendar.helpers.toTimeString
import java.util.*

class ScheduleViewModel(activity: Activity, dao: SchedulesTableDao, scheduleId: Long?) :
    InputViewModel<Schedule>(activity, dao, scheduleId, Schedule()) {

    val whenTime = Transformations.map(modelMutableLiveData) {
        modelMutableLiveData.value?.whenTime?.toTimeString(activity.applicationContext)
    }

    val startDate = Transformations.map(modelMutableLiveData) {
        modelMutableLiveData.value?.startDate?.toDateString(activity.applicationContext)
    }

    val endDate = Transformations.map(modelMutableLiveData) {
        modelMutableLiveData.value?.endDate?.toDateString(activity.applicationContext)
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
}