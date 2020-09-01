package com.broprojects.studentcalendar.schedule

import android.app.Activity
import com.broprojects.studentcalendar.database.Schedule
import com.broprojects.studentcalendar.database.SchedulesTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel

class ScheduleViewModel(activity: Activity, private val dao: SchedulesTableDao) : InputViewModel(activity) {
    fun saveData(data: Schedule) {
        super.saveData(data.scheduleId, data, dao)
    }
}