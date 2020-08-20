package com.broprojects.studentcalendar.person

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R

class PersonViewModel(private val activity: Activity) : ViewModel() {
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