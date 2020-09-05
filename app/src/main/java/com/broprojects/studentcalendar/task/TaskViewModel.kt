package com.broprojects.studentcalendar.task

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.broprojects.studentcalendar.R
import com.broprojects.studentcalendar.database.Task
import com.broprojects.studentcalendar.database.TasksTableDao
import com.broprojects.studentcalendar.helpers.InputViewModel
import com.broprojects.studentcalendar.helpers.ValueDropdownItem
import com.broprojects.studentcalendar.helpers.toDateTimeString
import java.util.*
import java.util.concurrent.TimeUnit


class TaskViewModel(activity: Activity, dao: TasksTableDao, private val taskId: Long?) :
    InputViewModel<Task>(activity, dao) {
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

    val remindersTextMap = mapOf(
        Pair(TimeUnit.MINUTES.toMillis(5), getString(R.string.five_min)),
        Pair(TimeUnit.MINUTES.toMillis(10), getString(R.string.ten_min)),
        Pair(TimeUnit.MINUTES.toMillis(15), getString(R.string.fifteen_min)),
        Pair(TimeUnit.MINUTES.toMillis(30), getString(R.string.thirty_min)),
        Pair(TimeUnit.HOURS.toMillis(1), getString(R.string.one_h)),
        Pair(TimeUnit.HOURS.toMillis(2), getString(R.string.two_h)),
        Pair(TimeUnit.HOURS.toMillis(12), getString(R.string.twelve_h)),
        Pair(TimeUnit.DAYS.toMillis(1), getString(R.string.one_day)),
        Pair(TimeUnit.DAYS.toMillis(2), getString(R.string.two_days)),
        Pair(TimeUnit.DAYS.toMillis(7), getString(R.string.one_week))
    )

    val priorityArray = arrayOf(
        ValueDropdownItem(getString(R.string.high), 2),
        ValueDropdownItem(getString(R.string.normal), 1),
        ValueDropdownItem(getString(R.string.low), 0)
    )

    val priorityTextMap = mapOf(
        Pair(2L, getString(R.string.high)),
        Pair(1L, getString(R.string.normal)),
        Pair(0L, getString(R.string.low))
    )

    private val _task = MutableLiveData(Task())
    val task: LiveData<Task>
        get() = _task

    val whenDateTime = Transformations.map(task) {
        task.value?.whenDateTime?.toDateTimeString(activity.applicationContext)
    }

    init {
        if (taskId != null) {
            _task.value = getData(taskId)
        }
    }

    fun setPriority(priority: Long) {
        _task.value?.priority = priority
    }

    fun setReminder(reminderTime: Long) {
        _task.value?.reminder = reminderTime
    }

    fun setWhenDateTime(whenDateTime: Date) {
        _task.value?.whenDateTime = whenDateTime
    }

    fun saveData() {
        super.saveData(taskId, _task.value!!)
    }
}