package com.broprojects.studentcalendar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.broprojects.studentcalendar.R

class MainViewModel : ViewModel() {
    private val drawablesMap = mapOf(
        Pair(R.drawable.ic_baseline_beach_access_140, R.drawable.ic_baseline_beach_access_40),
        Pair(R.drawable.ic_baseline_emoji_food_beverage_140, R.drawable.ic_baseline_emoji_food_beverage_40),
        Pair(R.drawable.ic_baseline_local_cafe_140, R.drawable.ic_baseline_local_cafe_40),
        Pair(R.drawable.ic_baseline_mood_140, R.drawable.ic_baseline_mood_40),
        Pair(R.drawable.ic_baseline_music_note_140, R.drawable.ic_baseline_music_note_40),
        Pair(R.drawable.ic_baseline_thumb_up_140, R.drawable.ic_baseline_thumb_up_40),
        Pair(R.drawable.ic_baseline_wb_sunny_140, R.drawable.ic_baseline_wb_sunny_40)
    )

    fun getSmallDrawableId(bigDrawableId: Int) =
        drawablesMap[bigDrawableId] ?: R.drawable.ic_baseline_wb_sunny_40

    private val _welcomeFragmentEvent = MutableLiveData<Boolean>()
    val welcomeFragmentEvent: LiveData<Boolean>
        get() = _welcomeFragmentEvent

    fun goToWelcomeFragment() {
        _welcomeFragmentEvent.value = true
    }

    fun goToWelcomeFragmentDone() {
        _welcomeFragmentEvent.value = false
    }
}