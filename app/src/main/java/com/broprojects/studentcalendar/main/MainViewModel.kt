package com.broprojects.studentcalendar.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R

class MainViewModel(activity: Activity) : ViewModel() {
    private val drawablesMap = mapOf(
        Pair(R.drawable.ic_baseline_beach_access_140, R.drawable.ic_baseline_beach_access_24),
        Pair(R.drawable.ic_baseline_emoji_food_beverage_140, R.drawable.ic_baseline_emoji_food_beverage_24),
        Pair(R.drawable.ic_baseline_local_cafe_140, R.drawable.ic_baseline_local_cafe_24),
        Pair(R.drawable.ic_baseline_mood_140, R.drawable.ic_baseline_mood_24),
        Pair(R.drawable.ic_baseline_music_note_140, R.drawable.ic_baseline_music_note_24),
        Pair(R.drawable.ic_baseline_thumb_up_140, R.drawable.ic_baseline_thumb_up_24),
        Pair(R.drawable.ic_baseline_wb_sunny_140, R.drawable.ic_baseline_wb_sunny_24)
    )

    private val _text = MutableLiveData<Int>()
    val text: LiveData<Int>
        get() = _text

    private val _icon = MutableLiveData<Int>()
    val icon: LiveData<Int>
        get() = _icon

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
        _icon.value = sharedPreferences.getInt(
            activity.getString(R.string.random_welcome_icon),
            R.drawable.ic_baseline_beach_access_140
        )

        // Indicate that goToWelcomeFragment event is done and action bar can be responsive again
        sharedPreferences.edit()
            .putBoolean(activity.getString(R.string.go_to_welcome_event), false)
            .apply()
    }

    // Get "...140" icon ID and return corresponding "...24" icon ID
    fun getSmallDrawableId(bigDrawableId: Int) =
        drawablesMap[bigDrawableId] ?: R.drawable.ic_baseline_beach_access_24
}