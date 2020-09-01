package com.broprojects.studentcalendar.task

import android.app.Activity
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.database.Task
import com.broprojects.studentcalendar.database.TasksTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel
import java.util.concurrent.TimeUnit

class TaskViewModel(activity: Activity, private val dao: TasksTableDao) : InputViewModel(activity) {
    val remindersArray = arrayOf(
        ValueDropdownItem(getString(R.string.five_min), TimeUnit.MINUTES.toMillis(5)),
        ValueDropdownItem(getString(R.string.ten_min), TimeUnit.MINUTES.toMillis(10)),
        ValueDropdownItem(getString(R.string.fifteen_min), TimeUnit.MINUTES.toMillis(15)),
        ValueDropdownItem(getString(R.string.thirty_min), TimeUnit.MINUTES.toMillis(30)),
        ValueDropdownItem(getString(R.string.one_h), TimeUnit.HOURS.toMillis(1)),
        ValueDropdownItem(getString(R.string.two_h), TimeUnit.HOURS.toMillis(2)),
        ValueDropdownItem(getString(R.string.twelve_h), TimeUnit.HOURS.toMillis(12)),
        ValueDropdownItem(getString(R.string.one_day), TimeUnit.DAYS.toMillis(1)),
        ValueDropdownItem(getString(R.string.two_days), TimeUnit.DAYS.toMillis(2)),
        ValueDropdownItem(getString(R.string.one_week), TimeUnit.DAYS.toMillis(7))
    )
    
    val priorityArray = arrayOf(
        ValueDropdownItem(getString(R.string.high), 2),
        ValueDropdownItem(getString(R.string.normal), 1),
        ValueDropdownItem(getString(R.string.low), 0)
    )

    fun saveData(data: Task) {
        super.saveData(data.taskId, data, dao)
    }
}