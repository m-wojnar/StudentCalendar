package com.broprojects.studentcalendar.task

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R
import java.util.concurrent.TimeUnit

class TaskViewModel(private val activity: Activity) : ViewModel() {
    val remindersArray = arrayOf(
        ValueDropdownItem(activity.getString(R.string.five_min), TimeUnit.MINUTES.toMillis(5)),
        ValueDropdownItem(activity.getString(R.string.ten_min), TimeUnit.MINUTES.toMillis(10)),
        ValueDropdownItem(activity.getString(R.string.fifteen_min), TimeUnit.MINUTES.toMillis(15)),
        ValueDropdownItem(activity.getString(R.string.thirty_min), TimeUnit.MINUTES.toMillis(30)),
        ValueDropdownItem(activity.getString(R.string.one_h), TimeUnit.HOURS.toMillis(1)),
        ValueDropdownItem(activity.getString(R.string.two_h), TimeUnit.HOURS.toMillis(2)),
        ValueDropdownItem(activity.getString(R.string.twelve_h), TimeUnit.HOURS.toMillis(12)),
        ValueDropdownItem(activity.getString(R.string.one_day), TimeUnit.DAYS.toMillis(1)),
        ValueDropdownItem(activity.getString(R.string.two_days), TimeUnit.DAYS.toMillis(2)),
        ValueDropdownItem(activity.getString(R.string.one_week), TimeUnit.DAYS.toMillis(7))
    )
    
    val priorityArray = arrayOf(
        ValueDropdownItem(activity.getString(R.string.high), 2),
        ValueDropdownItem(activity.getString(R.string.normal), 1),
        ValueDropdownItem(activity.getString(R.string.low), 0)
    )

    private val _colorStateList = MutableLiveData<ColorStateList>()
    val colorStateList: LiveData<ColorStateList>
        get() = _colorStateList

    private val _goToMainFragment = MutableLiveData<Boolean>()
    val goToMainFragment: LiveData<Boolean>
        get() = _goToMainFragment

    init {
        // Read chosen color from shared preferences
        val preferences = activity.getPreferences(Context.MODE_PRIVATE)
        val colorId = preferences.getInt(activity.getString(R.string.random_welcome_color), R.color.primary_color)
        _colorStateList.value = ContextCompat.getColorStateList(activity.applicationContext, colorId)!!
    }

    fun saveData() {
        // TODO add item to database
        _goToMainFragment.value = true
    }

    fun goToMainFragmentDone() {
        _goToMainFragment.value = false
    }
}