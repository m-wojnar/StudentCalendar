package com.mwojnar.studentcalendar.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mwojnar.studentcalendar.R

class MainViewModel(activity: Activity) : ViewModel() {
    private val _text = MutableLiveData<Int>()
    val text: LiveData<Int>
        get() = _text

    private val _color = MutableLiveData<Int>()
    val color: LiveData<Int>
        get() = _color

    init {
        // Get chosen color, text and icon from shared preferences
        val sharedPreferences = activity.getPreferences(Context.MODE_PRIVATE)
        _color.value = sharedPreferences.getInt(
            activity.getString(R.string.random_welcome_color),
            R.color.app_color_4
        )
        _text.value = sharedPreferences.getInt(
            activity.getString(R.string.random_welcome_text),
            R.string.welcome_6
        )
    }
}